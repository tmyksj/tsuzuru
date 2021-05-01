package tsuzuru.common.useCase.command

interface AbstractCommand<Request : AbstractCommand.Request, Response : AbstractCommand.Response> {

    fun perform(request: Request): Response

    interface Request

    interface Response

}
