package tsuzuru.infrastructure.useCase.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tsuzuru.domain.entity.ItemEntity
import tsuzuru.infrastructure.jpa.repository.TblItemRepository
import tsuzuru.infrastructure.repository.impl.ItemRepositoryImpl
import tsuzuru.infrastructure.sudachi.service.MorphologicalAnalysisService
import tsuzuru.security.principal.Principal
import tsuzuru.security.service.SecurityService
import tsuzuru.useCase.UserGetsInformationUseCase
import java.time.LocalDateTime

@Component
@Transactional
class UserGetsInformationUseCaseImpl(
    private val tblItemRepository: TblItemRepository,
    private val itemRepositoryImpl: ItemRepositoryImpl,
    private val morphologicalAnalysisService: MorphologicalAnalysisService,
    private val securityService: SecurityService,
) : UserGetsInformationUseCase {

    override fun perform(
        request: UserGetsInformationUseCase.Request,
    ): UserGetsInformationUseCase.Response {
        val principal: Principal = securityService.principal()

        val itemEntityList: List<ItemEntity> = itemRepositoryImpl.run {
            tblItemRepository
                .findByTblUserUuidAndWrittenDateGreaterThanEqualOrderByWrittenDateDesc(
                    tblUserUuid = principal.userEntity.uuid.toString(),
                    writtenDate = LocalDateTime.now().minusMonths(1),
                ).toDomainEntity()
        }

        val nounList = itemEntityList.flatMap { morphologicalAnalysisService.pickNoun(it.body) }
            .groupBy { it }
            .values
            .sortedByDescending { it.size }
            .map { it.first() }

        return UserGetsInformationUseCase.Response(
            nounList = nounList,
            size = itemEntityList.size,
        )
    }

}
