package tsuzuru.useCase.command

import tsuzuru.common.useCase.command.AbstractCommand
import tsuzuru.common.useCase.exception.UseCaseException
import java.util.*

interface UserModifiesItemCommand
    : AbstractCommand<UserModifiesItemCommand.Request, UserModifiesItemCommand.Response> {

    class Request(
        val uuid: UUID,
        val body: String,
    ) : AbstractCommand.Request

    class Response : AbstractCommand.Response

    class ItemIsNotFoundException : UseCaseException()

}
