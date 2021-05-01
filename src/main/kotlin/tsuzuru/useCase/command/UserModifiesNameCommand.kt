package tsuzuru.useCase.command

import tsuzuru.common.useCase.command.AbstractCommand
import tsuzuru.common.useCase.exception.UseCaseException

interface UserModifiesNameCommand
    : AbstractCommand<UserModifiesNameCommand.Request, UserModifiesNameCommand.Response> {

    class Request(
        val name: String,
    ) : AbstractCommand.Request

    class Response : AbstractCommand.Response

    class NameIsAlreadyInUseException : UseCaseException()

}
