package tsuzuru.useCase.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tsuzuru.domain.entity.UserEntity
import tsuzuru.domain.repository.UserRepository
import tsuzuru.useCase.GuestSignsUpUseCase

@Component
@Transactional
class GuestSignsUpUseCaseImpl(
    private val userRepository: UserRepository,
) : GuestSignsUpUseCase {

    override fun perform(
        request: GuestSignsUpUseCase.Request,
    ): GuestSignsUpUseCase.Response {
        try {
            val userEntity: UserEntity = UserEntity.build(
                name = request.name,
                passwordRaw = request.passwordRaw,
            )

            userRepository.save(userEntity)

            return GuestSignsUpUseCase.Response()
        } catch (e: UserRepository.NameMustBeUniqueException) {
            throw GuestSignsUpUseCase.NameIsAlreadyInUseException()
        }
    }

}
