package tsuzuru.useCase.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tsuzuru.domain.entity.ItemEntity
import tsuzuru.domain.repository.ItemRepository
import tsuzuru.security.principal.Principal
import tsuzuru.security.service.SecurityService
import tsuzuru.useCase.UserModifiesItemUseCase

@Component
@Transactional
class UserModifiesItemUseCaseImpl(
    private val itemRepository: ItemRepository,
    private val securityService: SecurityService,
) : UserModifiesItemUseCase {

    override fun perform(
        request: UserModifiesItemUseCase.Request,
    ): UserModifiesItemUseCase.Response {
        val principal: Principal = securityService.principal()

        val itemEntity: ItemEntity? = itemRepository.findByUuid(request.uuid)
        if (itemEntity == null || itemEntity.userEntity != principal.userEntity) {
            throw UserModifiesItemUseCase.ItemIsNotFoundException()
        }

        itemRepository.save(
            itemEntity.modifyBody(request.body)
        )

        return UserModifiesItemUseCase.Response()
    }

}
