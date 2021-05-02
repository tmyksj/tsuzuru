package tsuzuru.useCase.command.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tsuzuru.domain.entity.ItemEntity
import tsuzuru.domain.repository.ItemRepository
import tsuzuru.security.principal.Principal
import tsuzuru.security.service.SecurityService
import tsuzuru.useCase.command.UserDeletesItemCommand

@Component
@Transactional
class UserDeletesItemCommandImpl(
    private val itemRepository: ItemRepository,
    private val securityService: SecurityService,
) : UserDeletesItemCommand {

    override fun perform(
        request: UserDeletesItemCommand.Request,
    ): UserDeletesItemCommand.Response {
        val principal: Principal = securityService.principal()

        val itemEntity: ItemEntity? = itemRepository.findByUuid(request.uuid)
        if (itemEntity == null || itemEntity.userEntity != principal.userEntity) {
            throw UserDeletesItemCommand.ItemIsNotFoundException()
        }

        itemRepository.delete(itemEntity)

        return UserDeletesItemCommand.Response()
    }

}
