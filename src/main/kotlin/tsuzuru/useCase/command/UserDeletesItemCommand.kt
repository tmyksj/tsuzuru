package tsuzuru.useCase.command

import tsuzuru.common.useCase.command.AbstractCommand
import tsuzuru.common.useCase.exception.UseCaseException
import java.util.*

interface UserDeletesItemCommand
    : AbstractCommand<UserDeletesItemCommand.Request, UserDeletesItemCommand.Response> {

    class Request(
        val uuid: UUID,
    ) : AbstractCommand.Request

    class Response : AbstractCommand.Response

    class ItemIsNotFoundException : UseCaseException()

}
