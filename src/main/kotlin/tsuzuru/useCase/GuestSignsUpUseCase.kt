package tsuzuru.useCase

import tsuzuru.common.useCase.UseCase
import tsuzuru.common.useCase.exception.UseCaseException

interface GuestSignsUpUseCase
    : UseCase<GuestSignsUpUseCase.Request, GuestSignsUpUseCase.Response> {

    class Request(
        val name: String,
        val passwordRaw: String,
    ) : UseCase.Request

    class Response : UseCase.Response

    class NameIsAlreadyInUseException : UseCaseException()

}
