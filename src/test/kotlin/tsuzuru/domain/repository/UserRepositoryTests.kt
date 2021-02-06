package tsuzuru.domain.repository

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tsuzuru.domain.entity.UserEntity
import tsuzuru.test.EntitySupport
import java.util.*

@SpringBootTest
class UserRepositoryTests {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var entitySupport: EntitySupport

    @Test
    fun delete_deletes_entity() {
        val userEntity: UserEntity = entitySupport.userEntity()
        userRepository.delete(userEntity)

        val entity: UserEntity? = userRepository.findByUuid(userEntity.uuid)
        Assertions.assertThat(entity).isNull()
    }

    @Test
    fun findByName_returns_entity() {
        val userEntity: UserEntity = entitySupport.userEntity()
        val entity: UserEntity? = userRepository.findByName(userEntity.name)
        Assertions.assertThat(entity).isEqualTo(userEntity)
    }

    @Test
    fun findByName_returns_null_when_entity_does_not_exist() {
        val entity: UserEntity? = userRepository.findByName(UUID.randomUUID().toString())
        Assertions.assertThat(entity).isNull()
    }

    @Test
    fun findByUuid_returns_entity() {
        val userEntity: UserEntity = entitySupport.userEntity()
        val entity: UserEntity? = userRepository.findByUuid(userEntity.uuid)
        Assertions.assertThat(entity).isEqualTo(userEntity)
    }

    @Test
    fun findByUuid_returns_null_when_entity_does_not_exist() {
        val entity: UserEntity? = userRepository.findByUuid(UUID.randomUUID())
        Assertions.assertThat(entity).isNull()
    }

    @Test
    fun save_throws_NameMustBeUniqueException_when_name_is_already_in_use() {
        Assertions.assertThatThrownBy {
            userRepository.save(entitySupport.userEntity().modifyName(entitySupport.userEntity().name))
        }.isInstanceOf(UserRepository.NameMustBeUniqueException::class.java)
    }

}
