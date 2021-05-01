package tsuzuru.useCase.command.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tsuzuru.domain.entity.UserEntity
import tsuzuru.domain.repository.UserRepository
import tsuzuru.security.principal.Principal
import tsuzuru.security.service.SecurityService
import tsuzuru.useCase.command.UserModifiesNameCommand

@Component
@Transactional
class UserModifiesNameCommandImpl(
    private val userRepository: UserRepository,
    private val securityService: SecurityService,
) : UserModifiesNameCommand {

    override fun perform(
        request: UserModifiesNameCommand.Request,
    ): UserModifiesNameCommand.Response {
        try {
            val principal: Principal = securityService.principal()

            val userEntity: UserEntity = principal.userEntity.modifyName(request.name)
            userRepository.save(userEntity)

            securityService.reloadPrincipal()

            return UserModifiesNameCommand.Response()
        } catch (e: UserRepository.NameMustBeUniqueException) {
            throw UserModifiesNameCommand.NameIsAlreadyInUseException()
        }
    }

}
