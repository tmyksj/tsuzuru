package tsuzuru.useCase.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tsuzuru.domain.entity.UserEntity
import tsuzuru.domain.repository.UserRepository
import tsuzuru.security.principal.Principal
import tsuzuru.security.service.SecurityService
import tsuzuru.useCase.UserModifiesPasswordUseCase

@Component
@Transactional
class UserModifiesPasswordUseCaseImpl(
    private val userRepository: UserRepository,
    private val securityService: SecurityService,
) : UserModifiesPasswordUseCase {

    override fun perform(
        request: UserModifiesPasswordUseCase.Request,
    ): UserModifiesPasswordUseCase.Response {
        try {
            val principal: Principal = securityService.principal()

            val userEntity: UserEntity = principal.userEntity
                .modifyPassword(request.currentPasswordRaw, request.newPasswordRaw)
            userRepository.save(userEntity)

            securityService.reloadPrincipal()

            return UserModifiesPasswordUseCase.Response()
        } catch (e: UserEntity.PasswordMustMatchException) {
            throw UserModifiesPasswordUseCase.PasswordMismatchesException()
        }
    }

}
