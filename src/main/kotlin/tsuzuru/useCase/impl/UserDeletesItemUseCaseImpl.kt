package tsuzuru.useCase.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tsuzuru.domain.entity.ItemEntity
import tsuzuru.domain.repository.ItemRepository
import tsuzuru.security.principal.Principal
import tsuzuru.security.service.SecurityService
import tsuzuru.useCase.UserDeletesItemUseCase

@Component
@Transactional
class UserDeletesItemUseCaseImpl(
    private val itemRepository: ItemRepository,
    private val securityService: SecurityService,
) : UserDeletesItemUseCase {

    override fun perform(
        request: UserDeletesItemUseCase.Request,
    ): UserDeletesItemUseCase.Response {
        val principal: Principal = securityService.principal()

        val itemEntity: ItemEntity? = itemRepository.findByUuid(request.uuid)
        if (itemEntity == null || itemEntity.userEntity != principal.userEntity) {
            throw UserDeletesItemUseCase.ItemIsNotFoundException()
        }

        itemRepository.delete(itemEntity)

        return UserDeletesItemUseCase.Response()
    }

}
