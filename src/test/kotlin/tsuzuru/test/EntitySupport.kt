package tsuzuru.test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestComponent
import tsuzuru.domain.entity.UserEntity
import tsuzuru.domain.repository.UserRepository
import java.util.*

@TestComponent
class EntitySupport {

    @Autowired
    private lateinit var userRepository: UserRepository

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
