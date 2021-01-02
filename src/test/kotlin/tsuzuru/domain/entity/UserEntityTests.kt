package tsuzuru.domain.entity

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import tsuzuru.test.EntitySupport
import java.util.*

@SpringBootTest
class UserEntityTests {

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var entitySupport: EntitySupport

    @Test
    fun modifyName_modifies_name() {
        val name: String = UUID.randomUUID().toString()
        val entity: UserEntity = entitySupport.userEntity().modifyName(name)
        Assertions.assertThat(entity.name).isEqualTo(name)
    }

    @Test
    fun modifyPassword_modifies_passwordEncrypted() {
        val entity: UserEntity = entitySupport.userEntity().modifyPassword("passwordRaw", "newPasswordRaw")
        Assertions.assertThat(passwordEncoder.matches("newPasswordRaw", entity.passwordEncrypted)).isTrue
    }

    @Test
    fun modifyPassword_throws_PasswordMustMatchException_when_currentPasswordRaw_is_wrong() {
        Assertions.assertThatThrownBy {
            entitySupport.userEntity().modifyPassword("wrongPasswordRaw", "newPasswordRaw")
        }.isInstanceOf(UserEntity.PasswordMustMatchException::class.java)
    }

}
