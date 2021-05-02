package tsuzuru.useCase.command.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tsuzuru.domain.entity.UserEntity
import tsuzuru.domain.repository.UserRepository
import tsuzuru.security.principal.Principal
import tsuzuru.security.service.SecurityService
import tsuzuru.useCase.command.UserModifiesProfileCommand

@Component
@Transactional
class UserModifiesProfileCommandImpl(
    private val userRepository: UserRepository,
    private val securityService: SecurityService,
) : UserModifiesProfileCommand {

    override fun perform(
        request: UserModifiesProfileCommand.Request,
    ): UserModifiesProfileCommand.Response {
        val principal: Principal = securityService.principal()

        val userEntity: UserEntity = principal.userEntity
            .modifyProfileName(request.profileName)
            .modifyScope(request.scope)
        userRepository.save(userEntity)

        securityService.reloadPrincipal()

        return UserModifiesProfileCommand.Response()
    }

}
