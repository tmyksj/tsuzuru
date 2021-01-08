package tsuzuru.useCase

import tsuzuru.common.useCase.UseCase
import tsuzuru.common.useCase.exception.UseCaseException

interface UserModifiesPasswordUseCase
    : UseCase<UserModifiesPasswordUseCase.Request, UserModifiesPasswordUseCase.Response> {

    class Request(
        val currentPasswordRaw: String,
        val newPasswordRaw: String
    ) : UseCase.Request

    class Response : UseCase.Response

    class PasswordMismatchesException : UseCaseException()

}
