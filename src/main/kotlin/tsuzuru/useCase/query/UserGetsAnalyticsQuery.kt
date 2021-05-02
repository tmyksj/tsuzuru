package tsuzuru.useCase.query

import tsuzuru.common.useCase.query.AbstractQuery
import java.time.LocalDate

interface UserGetsAnalyticsQuery
    : AbstractQuery<UserGetsAnalyticsQuery.Request, UserGetsAnalyticsQuery.Response> {

    class Request(
        val range: ClosedRange<LocalDate>,
    ) : AbstractQuery.Request

    class Response(
        val dayList: List<Day>,
        val nounList: List<Noun>,
        val size: Int,
    ) : AbstractQuery.Response

    data class Day(
        val date: LocalDate,
        val nounList: List<Noun>,
        val size: Int,
    )

    data class Noun(
        val value: String,
        val size: Int,
    )

}
