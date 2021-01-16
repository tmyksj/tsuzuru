package tsuzuru.useCase

import tsuzuru.common.useCase.UseCase
import java.time.LocalDateTime
import java.util.*

interface UserGetsInformationUseCase
    : UseCase<UserGetsInformationUseCase.Request, UserGetsInformationUseCase.Response> {

    class Request(
        val page: LocalDateTime,
        val size: Int,
    ) : UseCase.Request

    class Response(
        val itemList: List<Item>,
    ) : UseCase.Response

    data class Item(
        val uuid: UUID,
        val body: String,
        val writtenDate: LocalDateTime,
    )

}
