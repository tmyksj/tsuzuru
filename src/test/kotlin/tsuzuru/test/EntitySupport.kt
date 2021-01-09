package tsuzuru.test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestComponent
import tsuzuru.domain.entity.ItemEntity
import tsuzuru.domain.entity.UserEntity
import tsuzuru.domain.repository.ItemRepository
import tsuzuru.domain.repository.UserRepository
import java.time.LocalDateTime
import java.util.*

@TestComponent
class EntitySupport {

    @Autowired
    private lateinit var itemRepository: ItemRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    fun itemEntity(
        uuid: UUID = UUID.randomUUID(),
        userEntity: UserEntity = userEntity(),
        body: String = UUID.randomUUID().toString(),
        writtenDate: LocalDateTime = LocalDateTime.now(),
    ): ItemEntity {
        return itemRepository.save(
            ItemEntity.build(
                uuid = uuid,
                userEntity = userEntity,
                body = body,
                writtenDate = writtenDate,
            )
        )
    }

    fun userEntity(
        uuid: UUID = UUID.randomUUID(),
        name: String = UUID.randomUUID().toString(),
        passwordRaw: String = "passwordRaw",
        roleList: List<UserEntity.Role> = listOf(UserEntity.Role.User),
    ): UserEntity {
        return userRepository.save(
            UserEntity.build(
                uuid = uuid,
                name = name,
                passwordRaw = passwordRaw,
                roleList = roleList,
            )
        )
    }

}
