package tsuzuru.security.service

import org.springframework.security.core.userdetails.UserDetailsService
import tsuzuru.security.principal.Principal

interface SecurityService : UserDetailsService {

    fun isAuthenticated(): Boolean

    fun principal(): Principal

    fun reloadPrincipal()

}
