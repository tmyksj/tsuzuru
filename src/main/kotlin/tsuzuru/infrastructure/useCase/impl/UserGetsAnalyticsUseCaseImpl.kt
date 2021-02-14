package tsuzuru.infrastructure.useCase.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tsuzuru.domain.entity.ItemEntity
import tsuzuru.infrastructure.jpa.repository.TblItemRepository
import tsuzuru.infrastructure.repository.impl.ItemRepositoryImpl
import tsuzuru.infrastructure.sudachi.service.MorphologicalAnalysisService
import tsuzuru.security.principal.Principal
import tsuzuru.security.service.SecurityService
import tsuzuru.useCase.UserGetsAnalyticsUseCase
import java.time.LocalDate
import kotlin.streams.toList

@Component
@Transactional
class UserGetsAnalyticsUseCaseImpl(
    private val tblItemRepository: TblItemRepository,
    private val itemRepositoryImpl: ItemRepositoryImpl,
    private val morphologicalAnalysisService: MorphologicalAnalysisService,
    private val securityService: SecurityService,
) : UserGetsAnalyticsUseCase {

    override fun perform(
        request: UserGetsAnalyticsUseCase.Request,
    ): UserGetsAnalyticsUseCase.Response {
        val principal: Principal = securityService.principal()

        val itemEntityList: List<ItemEntity> = itemRepositoryImpl.run {
            tblItemRepository
                .findByTblUserUuidAndWrittenDateBetweenOrderByWrittenDateAsc(
                    tblUserUuid = principal.userEntity.uuid.toString(),
                    writtenDateStart = request.range.start.atStartOfDay(),
                    writtenDateEndInclusive = request.range.endInclusive.atStartOfDay().plusDays(1),
                ).toDomainEntity()
        }

        val withNounList: List<Pair<ItemEntity, List<String>>> = itemEntityList
            .map { Pair(it, morphologicalAnalysisService.pickNoun(it.body)) }

        val dayMap: Map<LocalDate, List<Pair<ItemEntity, List<String>>>> = withNounList
            .groupBy { it.first.writtenDate.toLocalDate() }

        val dayList: List<UserGetsAnalyticsUseCase.Day> = request.range.start
            .datesUntil(request.range.endInclusive.plusDays(1)).toList()
            .map {
                UserGetsAnalyticsUseCase.Day(
                    date = it,
                    nounInfoList = dayMap[it]?.toNounList() ?: listOf(),
                    size = dayMap[it]?.size ?: 0,
                )
            }

        return UserGetsAnalyticsUseCase.Response(
            dayList = dayList,
            nounInfoList = withNounList.toNounList(),
            size = itemEntityList.size,
        )
    }

    private fun List<Pair<ItemEntity, List<String>>>.toNounList(): List<UserGetsAnalyticsUseCase.NounInfo> {
        return flatMap { it.second }
            .groupBy { it }
            .values
            .sortedByDescending { it.size }
            .map {
                UserGetsAnalyticsUseCase.NounInfo(
                    noun = it.first(),
                    size = it.size,
                )
            }
    }

}
