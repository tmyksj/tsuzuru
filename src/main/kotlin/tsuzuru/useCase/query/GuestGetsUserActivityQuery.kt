package tsuzuru.useCase.query

import tsuzuru.common.useCase.exception.UseCaseException
import tsuzuru.common.useCase.query.AbstractQuery
import java.time.LocalDate
import java.time.LocalDateTime

interface GuestGetsUserActivityQuery
    : AbstractQuery<GuestGetsUserActivityQuery.Request, GuestGetsUserActivityQuery.Response> {

    class Request(
        val name: String,
        val range: ClosedRange<LocalDate>,
        val page: LocalDateTime,
        val size: Int,
    ) : AbstractQuery.Request

    class Response(
        val name: String,
        val profileName: String,
        val analytics: Analytics,
        val itemList: List<Item>,
    ) : AbstractQuery.Response

    class UserIsNotFoundException : UseCaseException()

    data class Analytics(
        val nounList: List<Noun>,
        val size: Int,
    )

    data class Item(
        val body: String,
        val writtenDate: LocalDateTime,
    )

    data class Noun(
        val value: String,
        val size: Int,
    )

}
