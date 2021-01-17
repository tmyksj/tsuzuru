package tsuzuru.useCase

import tsuzuru.common.useCase.UseCase

interface UserGetsInformationUseCase
    : UseCase<UserGetsInformationUseCase.Request, UserGetsInformationUseCase.Response> {

    class Request : UseCase.Request

    class Response(
        val nounList: List<String>,
        val size: Int,
    ) : UseCase.Response

}
