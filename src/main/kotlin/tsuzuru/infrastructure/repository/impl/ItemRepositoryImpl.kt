package tsuzuru.infrastructure.repository.impl

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tsuzuru.common.infrastructure.repository.impl.AbstractRepositoryImpl
import tsuzuru.domain.entity.ItemEntity
import tsuzuru.domain.entity.UserEntity
import tsuzuru.domain.repository.ItemRepository
import tsuzuru.infrastructure.jpa.entity.TblItemEntity
import tsuzuru.infrastructure.jpa.entity.TblUserEntity
import tsuzuru.infrastructure.jpa.repository.TblItemRepository
import tsuzuru.infrastructure.jpa.repository.TblUserRepository
import java.util.*

@Component
@Transactional
class ItemRepositoryImpl(
    private val tblItemRepository: TblItemRepository,
    private val tblUserRepository: TblUserRepository,
    private val userRepositoryImpl: UserRepositoryImpl,
) : AbstractRepositoryImpl(), ItemRepository {

    override fun findByUuid(uuid: UUID): ItemEntity? {
        return tblItemRepository.findByIdOrNull(uuid.toString())?.toDomainEntity()
    }

    override fun initialize() {
    }

    override fun save(entity: ItemEntity): ItemEntity {
        val tblItemEntity: TblItemEntity = save(
            repository = tblItemRepository,
            entity = entity,
            tblEntity = tblItemRepository.findByIdOrNull(entity.uuid.toString()),
            tblEntitySetter = {
                uuid = it.uuid.toString()
                tblUserUuid = it.userEntity.uuid.toString()
                body = it.body
                writtenDate = it.writtenDate
            }
        )

        return tblItemEntity.toDomainEntity()
    }

    fun TblItemEntity.toDomainEntity(): ItemEntity {
        val userEntity: UserEntity = userRepositoryImpl.run {
            val tblUserEntity: TblUserEntity? = tblUserRepository.findByIdOrNull(tblUserUuid)
            checkNotNull(tblUserEntity).toDomainEntity()
        }

        return ItemEntity(
            uuid = UUID.fromString(checkNotNull(uuid)),
            userEntity = userEntity,
            body = checkNotNull(body),
            writtenDate = checkNotNull(writtenDate),
        )
    }

}
