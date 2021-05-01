package tsuzuru.useCase.command.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tsuzuru.domain.entity.ItemEntity
import tsuzuru.domain.repository.ItemRepository
import tsuzuru.security.principal.Principal
import tsuzuru.security.service.SecurityService
import tsuzuru.useCase.command.UserCreatesItemCommand

@Component
@Transactional
class UserCreatesItemCommandImpl(
    private val itemRepository: ItemRepository,
    private val securityService: SecurityService,
) : UserCreatesItemCommand {

    override fun perform(
        request: UserCreatesItemCommand.Request,
    ): UserCreatesItemCommand.Response {
        val principal: Principal = securityService.principal()

        val itemEntity: ItemEntity = ItemEntity.build(
            userEntity = principal.userEntity,
            body = request.body,
        )

        itemRepository.save(itemEntity)

        return UserCreatesItemCommand.Response()
    }

}
