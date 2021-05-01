package tsuzuru.useCase.command

import tsuzuru.common.useCase.command.AbstractCommand
import tsuzuru.common.useCase.exception.UseCaseException

interface UserModifiesPasswordCommand
    : AbstractCommand<UserModifiesPasswordCommand.Request, UserModifiesPasswordCommand.Response> {

    class Request(
        val currentPasswordRaw: String,
        val newPasswordRaw: String
    ) : AbstractCommand.Request

    class Response : AbstractCommand.Response

    class PasswordMismatchesException : UseCaseException()

}
