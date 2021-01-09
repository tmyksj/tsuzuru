package tsuzuru.security.service.impl

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import tsuzuru.domain.entity.UserEntity
import tsuzuru.domain.repository.UserRepository
import tsuzuru.security.principal.Principal
import tsuzuru.security.service.SecurityService
import javax.persistence.EntityManager

@Service
class SecurityServiceImpl(
    private val userRepository: UserRepository,
    private val entityManager: EntityManager,
) : SecurityService {

    override fun isAuthenticated(): Boolean {
        return SecurityContextHolder.getContext().authentication.principal is Principal
    }

    @Transactional
    override fun loadUserByUsername(username: String?): UserDetails {
        requireNotNull(username)

        val userEntity: UserEntity = userRepository.findByName(username)
            ?: throw UsernameNotFoundException("")

        return Principal(
            userEntity = userEntity,
        )
    }

    override fun principal(): Principal {
        return SecurityContextHolder.getContext().authentication.principal as? Principal
            ?: throw IllegalStateException()
    }

    @Transactional
    override fun reloadPrincipal() {
        entityManager.flush()

        val userEntity: UserEntity = userRepository.findByUuid(principal().userEntity.uuid)
            ?: throw IllegalStateException()

        val principal = Principal(
            userEntity = userEntity,
        )

        SecurityContextHolder.getContext().authentication =
            UsernamePasswordAuthenticationToken(principal, principal.password, principal.authorities)
    }

}
