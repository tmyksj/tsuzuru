package tsuzuru.useCase.query

import tsuzuru.common.useCase.query.AbstractQuery
import java.time.LocalDateTime
import java.util.*

interface UserGetsItemsQuery
    : AbstractQuery<UserGetsItemsQuery.Request, UserGetsItemsQuery.Response> {

    class Request(
        val page: LocalDateTime,
        val size: Int,
    ) : AbstractQuery.Request

    class Response(
        val itemList: List<Item>,
    ) : AbstractQuery.Response

    data class Item(
        val uuid: UUID,
        val body: String,
        val writtenDate: LocalDateTime,
    )

}
