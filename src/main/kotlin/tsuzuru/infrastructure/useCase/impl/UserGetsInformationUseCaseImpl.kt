package tsuzuru.infrastructure.useCase.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tsuzuru.domain.entity.ItemEntity
import tsuzuru.infrastructure.jpa.repository.TblItemRepository
import tsuzuru.infrastructure.repository.impl.ItemRepositoryImpl
import tsuzuru.security.principal.Principal
import tsuzuru.security.service.SecurityService
import tsuzuru.useCase.UserGetsInformationUseCase

@Component
@Transactional
class UserGetsInformationUseCaseImpl(
    private val tblItemRepository: TblItemRepository,
    private val itemRepositoryImpl: ItemRepositoryImpl,
    private val securityService: SecurityService,
) : UserGetsInformationUseCase {

    override fun perform(
        request: UserGetsInformationUseCase.Request,
    ): UserGetsInformationUseCase.Response {
        val principal: Principal = securityService.principal()

        val itemEntityList: List<ItemEntity> = itemRepositoryImpl.run {
            tblItemRepository
                .findByTblUserUuidOrderByWrittenDateAsc(principal.userEntity.uuid.toString())
                .toDomainEntity()
        }

        return UserGetsInformationUseCase.Response(
            itemEntityList = itemEntityList,
        )
    }

}