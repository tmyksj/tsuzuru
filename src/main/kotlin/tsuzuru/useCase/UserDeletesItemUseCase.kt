package tsuzuru.useCase

import tsuzuru.common.useCase.UseCase
import tsuzuru.common.useCase.exception.UseCaseException
import java.util.*

interface UserDeletesItemUseCase
    : UseCase<UserDeletesItemUseCase.Request, UserDeletesItemUseCase.Response> {

    class Request(
        val uuid: UUID,
    ) : UseCase.Request

    class Response : UseCase.Response

    class ItemIsNotFoundException : UseCaseException()

}
