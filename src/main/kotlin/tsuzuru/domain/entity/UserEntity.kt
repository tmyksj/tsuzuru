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
    val profileName: String,
    val scope: Scope,
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

    fun modifyProfileName(profileName: String): UserEntity {
        return copy(profileName = profileName)
    }

    fun modifyScope(scope: Scope): UserEntity {
        return copy(scope = scope)
    }

    enum class Role {
        Admin,
        User,
    }

    enum class Scope {
        Private,
        Public,
    }

    class PasswordMustMatchException : DomainException()

    companion object {

        fun build(
            uuid: UUID = UUID.randomUUID(),
            name: String,
            passwordEncrypted: String? = null,
            passwordRaw: String? = null,
            roleList: List<Role> = listOf(Role.User),
            profileName: String = name,
            scope: Scope = Scope.Private,
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
                profileName = profileName,
                scope = scope,
            )
        }

    }

}
