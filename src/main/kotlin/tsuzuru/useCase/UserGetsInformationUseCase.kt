package tsuzuru.useCase

import tsuzuru.common.useCase.UseCase
import tsuzuru.domain.entity.ItemEntity

interface UserGetsInformationUseCase
    : UseCase<UserGetsInformationUseCase.Request, UserGetsInformationUseCase.Response> {

    class Request : UseCase.Request

    class Response(
        val itemEntityList: List<ItemEntity>,
    ) : UseCase.Response

}
