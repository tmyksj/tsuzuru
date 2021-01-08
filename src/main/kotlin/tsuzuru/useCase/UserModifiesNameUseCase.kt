package tsuzuru.useCase

import tsuzuru.common.useCase.UseCase
import tsuzuru.common.useCase.exception.UseCaseException

interface UserModifiesNameUseCase
    : UseCase<UserModifiesNameUseCase.Request, UserModifiesNameUseCase.Response> {

    class Request(
        val name: String,
    ) : UseCase.Request

    class Response : UseCase.Response

    class NameIsAlreadyInUseException : UseCaseException()

}
