package tsuzuru.useCase

import tsuzuru.common.useCase.UseCase
import java.time.LocalDate

interface UserGetsAnalyticsUseCase
    : UseCase<UserGetsAnalyticsUseCase.Request, UserGetsAnalyticsUseCase.Response> {

    class Request(
        val range: ClosedRange<LocalDate>,
    ) : UseCase.Request

    class Response(
        val dayList: List<Day>,
        val nounList: List<Noun>,
        val size: Int,
    ) : UseCase.Response

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
