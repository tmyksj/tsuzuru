package tsuzuru.useCase.command

import tsuzuru.common.useCase.command.AbstractCommand
import tsuzuru.common.useCase.exception.UseCaseException

interface GuestSignsUpCommand
    : AbstractCommand<GuestSignsUpCommand.Request, GuestSignsUpCommand.Response> {

    class Request(
        val name: String,
        val passwordRaw: String,
    ) : AbstractCommand.Request

    class Response : AbstractCommand.Response

    class NameIsAlreadyInUseException : UseCaseException()

}
