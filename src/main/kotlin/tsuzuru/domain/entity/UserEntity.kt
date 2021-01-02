package tsuzuru.domain.entity

import org.springframework.security.crypto.password.PasswordEncoder
import tsuzuru.common.domain.entity.AbstractEntity
import tsuzuru.common.domain.exception.DomainException
import tsuzuru.common.support.BeanSupport
import java.util.*

data class UserEntity(
    val uuid: UUID,
    val name: String,
    val passwordEncrypted: String,
    val roleList: List<Role>,
) : AbstractEntity() {

    fun modifyName(name: String): UserEntity {
        return copy(name = name)
    }

    fun modifyPassword(currentPasswordRaw: String, newPasswordRaw: String): UserEntity {
        val passwordEncoder: PasswordEncoder = BeanSupport.getBean(PasswordEncoder::class)

        if (passwordEncoder.matches(currentPasswordRaw, passwordEncrypted)) {
            return copy(passwordEncrypted = passwordEncoder.encode(newPasswordRaw))
        } else {
            throw PasswordMustMatchException()
        }
    }

    enum class Role {
        Admin,
        User,
    }

    class PasswordMustMatchException : DomainException()

    companion object {

        fun build(
            uuid: UUID = UUID.randomUUID(),
            name: String,
            passwordEncrypted: String? = null,
            passwordRaw: String? = null,
            roleList: List<Role> = listOf(Role.User),
        ): UserEntity {
            val passwordEncoder: PasswordEncoder = BeanSupport.getBean(PasswordEncoder::class)

            if (passwordEncrypted == null && passwordRaw == null) {
                throw IllegalArgumentException()
            }

            return UserEntity(
                uuid = uuid,
                name = name,
                passwordEncrypted = passwordEncrypted ?: passwordEncoder.encode(passwordRaw),
                roleList = roleList,
            )
        }

    }

}
