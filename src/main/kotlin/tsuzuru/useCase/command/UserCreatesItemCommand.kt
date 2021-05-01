package tsuzuru.useCase.command

import tsuzuru.common.useCase.command.AbstractCommand

interface UserCreatesItemCommand
    : AbstractCommand<UserCreatesItemCommand.Request, UserCreatesItemCommand.Response> {

    class Request(
        val body: String,
    ) : AbstractCommand.Request

    class Response : AbstractCommand.Response

}
