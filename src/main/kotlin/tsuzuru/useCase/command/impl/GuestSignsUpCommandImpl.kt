package tsuzuru.useCase.command.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tsuzuru.domain.entity.UserEntity
import tsuzuru.domain.repository.UserRepository
import tsuzuru.useCase.command.GuestSignsUpCommand

@Component
@Transactional
class GuestSignsUpCommandImpl(
    private val userRepository: UserRepository,
) : GuestSignsUpCommand {

    override fun perform(
        request: GuestSignsUpCommand.Request,
    ): GuestSignsUpCommand.Response {
        try {
            val userEntity: UserEntity = UserEntity.build(
                name = request.name,
                passwordRaw = request.passwordRaw,
            )

            userRepository.save(userEntity)

            return GuestSignsUpCommand.Response()
        } catch (e: UserRepository.NameMustBeUniqueException) {
            throw GuestSignsUpCommand.NameIsAlreadyInUseException()
        }
    }

}
