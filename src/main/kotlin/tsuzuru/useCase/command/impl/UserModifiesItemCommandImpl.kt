package tsuzuru.useCase.command.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tsuzuru.domain.entity.ItemEntity
import tsuzuru.domain.repository.ItemRepository
import tsuzuru.security.principal.Principal
import tsuzuru.security.service.SecurityService
import tsuzuru.useCase.command.UserModifiesItemCommand

@Component
@Transactional
class UserModifiesItemCommandImpl(
    private val itemRepository: ItemRepository,
    private val securityService: SecurityService,
) : UserModifiesItemCommand {

    override fun perform(
        request: UserModifiesItemCommand.Request,
    ): UserModifiesItemCommand.Response {
        val principal: Principal = securityService.principal()

        val itemEntity: ItemEntity? = itemRepository.findByUuid(request.uuid)
        if (itemEntity == null || itemEntity.userEntity != principal.userEntity) {
            throw UserModifiesItemCommand.ItemIsNotFoundException()
        }

        itemRepository.save(
            itemEntity.modifyBody(request.body)
        )

        return UserModifiesItemCommand.Response()
    }

}
