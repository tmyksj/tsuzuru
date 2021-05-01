package tsuzuru.useCase.query

import tsuzuru.common.useCase.query.AbstractQuery

interface AdminGetsStatisticsQuery
    : AbstractQuery<AdminGetsStatisticsQuery.Request, AdminGetsStatisticsQuery.Response> {

    class Request : AbstractQuery.Request

    class Response(
        val userSize: Long,
        val itemSize: Long,
    ) : AbstractQuery.Response

}
