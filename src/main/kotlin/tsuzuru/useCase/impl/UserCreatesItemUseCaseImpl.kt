package tsuzuru.useCase.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tsuzuru.domain.entity.ItemEntity
import tsuzuru.domain.repository.ItemRepository
import tsuzuru.security.principal.Principal
import tsuzuru.security.service.SecurityService
import tsuzuru.useCase.UserCreatesItemUseCase

@Component
@Transactional
class UserCreatesItemUseCaseImpl(
    private val itemRepository: ItemRepository,
    private val securityService: SecurityService,
) : UserCreatesItemUseCase {

    override fun perform(
        request: UserCreatesItemUseCase.Request,
    ): UserCreatesItemUseCase.Response {
        val principal: Principal = securityService.principal()

        val itemEntity: ItemEntity = ItemEntity.build(
            userEntity = principal.userEntity,
            body = request.body,
        )

        itemRepository.save(itemEntity)

        return UserCreatesItemUseCase.Response()
    }

}
