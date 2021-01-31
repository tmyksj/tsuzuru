package tsuzuru.useCase

import tsuzuru.common.useCase.UseCase
import tsuzuru.common.useCase.exception.UseCaseException
import java.util.*

interface UserModifiesItemUseCase
    : UseCase<UserModifiesItemUseCase.Request, UserModifiesItemUseCase.Response> {

    class Request(
        val uuid: UUID,
        val body: String,
    ) : UseCase.Request

    class Response : UseCase.Response

    class ItemIsNotFoundException : UseCaseException()

}
