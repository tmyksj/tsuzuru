package tsuzuru.common.useCase

interface UseCase<Request : UseCase.Request, Response : UseCase.Response> {

    fun perform(request: Request): Response

    interface Request

    interface Response

}
