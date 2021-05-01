package tsuzuru.infrastructure.useCase.query.impl

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import tsuzuru.domain.entity.ItemEntity
import tsuzuru.infrastructure.jpa.repository.TblItemRepository
import tsuzuru.infrastructure.repository.impl.ItemRepositoryImpl
import tsuzuru.infrastructure.sudachi.service.MorphologicalAnalysisService
import tsuzuru.security.principal.Principal
import tsuzuru.security.service.SecurityService
import tsuzuru.useCase.query.UserGetsAnalyticsQuery
import java.time.LocalDate
import kotlin.streams.toList

@Component
@Transactional
class UserGetsAnalyticsQueryImpl(
    private val tblItemRepository: TblItemRepository,
    private val itemRepositoryImpl: ItemRepositoryImpl,
    private val morphologicalAnalysisService: MorphologicalAnalysisService,
    private val securityService: SecurityService,
) : UserGetsAnalyticsQuery {

    override fun perform(
        request: UserGetsAnalyticsQuery.Request,
    ): UserGetsAnalyticsQuery.Response {
        val principal: Principal = securityService.principal()

        val itemEntityList: List<ItemEntity> = itemRepositoryImpl.run {
            tblItemRepository
                .findByTblUserUuidAndWrittenDateBetweenOrderByWrittenDateAsc(
                    tblUserUuid = principal.userEntity.uuid.toString(),
                    writtenDateStart = request.range.start.atStartOfDay(),
                    writtenDateEndInclusive = request.range.endInclusive.atStartOfDay().plusDays(1),
                ).toDomainEntity()
        }

        val nounList: List<Pair<ItemEntity, List<String>>> = itemEntityList
            .map { Pair(it, morphologicalAnalysisService.pickNoun(it.body)) }

        val dayMap: Map<LocalDate, List<Pair<ItemEntity, List<String>>>> = nounList
            .groupBy { it.first.writtenDate.toLocalDate() }

        val dayList: List<UserGetsAnalyticsQuery.Day> = request.range.start
            .datesUntil(request.range.endInclusive.plusDays(1)).toList()
            .map {
                UserGetsAnalyticsQuery.Day(
                    date = it,
                    nounList = dayMap[it]?.format() ?: listOf(),
                    size = dayMap[it]?.size ?: 0,
                )
            }

        return UserGetsAnalyticsQuery.Response(
            dayList = dayList,
            nounList = nounList.format(),
            size = itemEntityList.size,
        )
    }

    private fun List<Pair<ItemEntity, List<String>>>.format(): List<UserGetsAnalyticsQuery.Noun> {
        return flatMap { it.second }
            .groupBy { it }
            .values
            .sortedByDescending { it.size }
            .map {
                UserGetsAnalyticsQuery.Noun(
                    value = it.first(),
                    size = it.size,
                )
            }
    }

}
