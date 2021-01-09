package tsuzuru.useCase.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tsuzuru.domain.entity.UserEntity
import tsuzuru.domain.repository.UserRepository
import tsuzuru.security.principal.Principal
import tsuzuru.security.service.SecurityService
import tsuzuru.useCase.UserModifiesNameUseCase

@Component
@Transactional
class UserModifiesNameUseCaseImpl(
    private val userRepository: UserRepository,
    private val securityService: SecurityService,
) : UserModifiesNameUseCase {

    override fun perform(
        request: UserModifiesNameUseCase.Request,
    ): UserModifiesNameUseCase.Response {
        try {
            val principal: Principal = securityService.principal()

            val userEntity: UserEntity = principal.userEntity.modifyName(request.name)
            userRepository.save(userEntity)

            securityService.reloadPrincipal()

            return UserModifiesNameUseCase.Response()
        } catch (e: UserRepository.NameMustBeUniqueException) {
            throw UserModifiesNameUseCase.NameIsAlreadyInUseException()
        }
    }

}
