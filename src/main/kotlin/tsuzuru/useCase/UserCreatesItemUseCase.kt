package tsuzuru.useCase

import tsuzuru.common.useCase.UseCase

interface UserCreatesItemUseCase
    : UseCase<UserCreatesItemUseCase.Request, UserCreatesItemUseCase.Response> {

    class Request(
        val body: String,
    ) : UseCase.Request

    class Response : UseCase.Response

}
