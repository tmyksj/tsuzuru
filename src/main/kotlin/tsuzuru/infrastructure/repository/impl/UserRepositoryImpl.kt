package tsuzuru.infrastructure.repository.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tsuzuru.common.infrastructure.repository.impl.AbstractRepositoryImpl
import tsuzuru.domain.entity.UserEntity
import tsuzuru.domain.repository.UserRepository
import tsuzuru.infrastructure.jpa.entity.EmUserRoleEntity
import tsuzuru.infrastructure.jpa.entity.TblUserEntity
import tsuzuru.infrastructure.jpa.entity.TblUserRoleEntity
import tsuzuru.infrastructure.jpa.repository.EmUserRoleRepository
import tsuzuru.infrastructure.jpa.repository.TblUserRepository
import tsuzuru.infrastructure.jpa.repository.TblUserRoleRepository
import java.util.*

@Component
@Transactional
class UserRepositoryImpl(
    private val emUserRoleRepository: EmUserRoleRepository,
    private val tblUserRepository: TblUserRepository,
    private val tblUserRoleRepository: TblUserRoleRepository,
) : AbstractRepositoryImpl(), UserRepository {

    override fun existsByRole(role: UserEntity.Role): Boolean {
        return tblUserRoleRepository.existsByIdEmUserRoleValue(role.toString())
    }

    override fun findByName(name: String): UserEntity? {
        return tblUserRepository.findByName(name)?.toDomainEntity()
    }

    override fun findByUuid(uuid: UUID): UserEntity? {
        return tblUserRepository.findByIdOrNull(uuid.toString())?.toDomainEntity()
    }

    override fun initialize() {
        val valueList: List<String> = UserEntity.Role.values().map(UserEntity.Role::toString)
        val entityList: List<EmUserRoleEntity> = emUserRoleRepository.findAll()

        emUserRoleRepository.deleteAll(entityList
            .filterNot { valueList.contains(it.value) })
        emUserRoleRepository.saveAll(valueList
            .filterNot { entityList.map(EmUserRoleEntity::value).contains(it) }
            .map { EmUserRoleEntity(value = it) })
    }

    override fun save(entity: UserEntity): UserEntity {
        val other: TblUserEntity? = tblUserRepository.findByName(entity.name)
        if (other != null && other.uuid != entity.uuid.toString()) {
            throw UserRepository.NameMustBeUniqueException()
        }

        val tblEntity: TblUserEntity = save(
            repository = tblUserRepository,
            entity = entity,
            tblEntity = tblUserRepository.findByIdOrNull(entity.uuid.toString()),
            tblEntitySetter = {
                uuid = it.uuid.toString()
                name = it.name
                passwordEncrypted = it.passwordEncrypted
            },
        )

        save(
            repository = tblUserRoleRepository,
            entityList = entity.roleList,
            entityKeySelector = { it.toString() },
            tblEntityList = tblUserRoleRepository.findByIdTblUserUuid(entity.uuid.toString()),
            tblEntityKeySelector = { id?.emUserRoleValue },
            tblEntitySetter = {
                id = TblUserRoleEntity.Id(
                    tblUserUuid = entity.uuid.toString(),
                    emUserRoleValue = it.toString(),
                )
            },
        )

        return tblEntity.toDomainEntity()
    }

    fun TblUserEntity.toDomainEntity(): UserEntity {
        return listOf(this).toDomainEntity().first()
    }

    fun List<TblUserEntity>.toDomainEntity(): List<UserEntity> {
        val roleListMap: Map<String, List<UserEntity.Role>> =
            tblUserRoleRepository
                .findByIdTblUserUuidIn(map { checkNotNull(it.uuid) })
                .groupBy(
                    { checkNotNull(it.id?.tblUserUuid) },
                    { UserEntity.Role.valueOf(checkNotNull(it.id?.emUserRoleValue)) })

        return map {
            UserEntity(
                uuid = UUID.fromString(checkNotNull(it.uuid)),
                name = checkNotNull(it.name),
                passwordEncrypted = checkNotNull(it.passwordEncrypted),
                roleList = checkNotNull(roleListMap[it.uuid])
            )
        }
    }

}
