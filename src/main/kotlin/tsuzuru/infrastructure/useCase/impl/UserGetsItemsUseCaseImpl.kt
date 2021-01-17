package tsuzuru.infrastructure.useCase.impl

import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tsuzuru.domain.entity.ItemEntity
import tsuzuru.infrastructure.jpa.repository.TblItemRepository
import tsuzuru.infrastructure.repository.impl.ItemRepositoryImpl
import tsuzuru.security.principal.Principal
import tsuzuru.security.service.SecurityService
import tsuzuru.useCase.UserGetsItemsUseCase

@Component
@Transactional
class UserGetsItemsUseCaseImpl(
    private val tblItemRepository: TblItemRepository,
    private val itemRepositoryImpl: ItemRepositoryImpl,
    private val securityService: SecurityService,
) : UserGetsItemsUseCase {

    override fun perform(
        request: UserGetsItemsUseCase.Request,
    ): UserGetsItemsUseCase.Response {
        val principal: Principal = securityService.principal()

        val itemEntityList: List<ItemEntity> = itemRepositoryImpl.run {
            tblItemRepository
                .findByTblUserUuidAndWrittenDateLessThanEqualOrderByWrittenDateDesc(
                    tblUserUuid = principal.userEntity.uuid.toString(),
                    writtenDate = request.page,
                    pageable = PageRequest.of(0, request.size),
                ).toDomainEntity()
        }

        return UserGetsItemsUseCase.Response(
            itemList = itemEntityList.map {
                UserGetsItemsUseCase.Item(
                    uuid = it.uuid,
                    body = it.body,
                    writtenDate = it.writtenDate,
                )
            },
        )
    }

}
