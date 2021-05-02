package tsuzuru.useCase.command.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tsuzuru.domain.entity.UserEntity
import tsuzuru.domain.repository.UserRepository
import tsuzuru.security.principal.Principal
import tsuzuru.security.service.SecurityService
import tsuzuru.useCase.command.UserModifiesPasswordCommand

@Component
@Transactional
class UserModifiesPasswordCommandImpl(
    private val userRepository: UserRepository,
    private val securityService: SecurityService,
) : UserModifiesPasswordCommand {

    override fun perform(
        request: UserModifiesPasswordCommand.Request,
    ): UserModifiesPasswordCommand.Response {
        try {
            val principal: Principal = securityService.principal()

            val userEntity: UserEntity = principal.userEntity
                .modifyPassword(request.currentPasswordRaw, request.newPasswordRaw)
            userRepository.save(userEntity)

            securityService.reloadPrincipal()

            return UserModifiesPasswordCommand.Response()
        } catch (e: UserEntity.PasswordMustMatchException) {
            throw UserModifiesPasswordCommand.PasswordMismatchesException()
        }
    }

}
