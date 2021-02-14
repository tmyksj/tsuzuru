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
        val nounInfoList: List<NounInfo>,
        val size: Int,
    ) : UseCase.Response

    data class Day(
        val date: LocalDate,
        val nounInfoList: List<NounInfo>,
        val size: Int,
    )

    data class NounInfo(
        val noun: String,
        val size: Int,
    )

}
