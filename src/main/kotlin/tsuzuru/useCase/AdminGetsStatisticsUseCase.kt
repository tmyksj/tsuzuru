package tsuzuru.useCase

import tsuzuru.common.useCase.UseCase

interface AdminGetsStatisticsUseCase
    : UseCase<AdminGetsStatisticsUseCase.Request, AdminGetsStatisticsUseCase.Response> {

    class Request : UseCase.Request

    class Response(
        val userSize: Long,
        val itemSize: Long,
    ) : UseCase.Response

}
