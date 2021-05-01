package tsuzuru.common.useCase.query

interface AbstractQuery<Request : AbstractQuery.Request, Response : AbstractQuery.Response> {

    fun perform(request: Request): Response

    interface Request

    interface Response

}
