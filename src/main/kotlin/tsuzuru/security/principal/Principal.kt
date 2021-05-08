package tsuzuru.security.principal

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import tsuzuru.domain.entity.UserEntity

data class Principal(
    val userEntity: UserEntity,
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return userEntity.roleList.map {
            SimpleGrantedAuthority("ROLE_${it.toString().uppercase()}")
        }.toMutableList()
    }

    override fun getPassword(): String {
        return userEntity.passwordEncrypted
    }

    override fun getUsername(): String {
        return userEntity.name
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}
