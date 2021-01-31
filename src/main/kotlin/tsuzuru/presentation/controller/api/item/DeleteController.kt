package tsuzuru.presentation.controller.api.item

import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import tsuzuru.common.presentation.controller.impl.AbstractControllerImpl
import tsuzuru.presentation.form.api.item.DeleteForm
import tsuzuru.useCase.UserDeletesItemUseCase
import java.util.*

@RestController
class DeleteController(
    private val userDeletesItemUseCase: UserDeletesItemUseCase,
) : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.POST], path = ["/api/item/{uuid}/delete"])
    fun post(
        @Validated form: DeleteForm,
        bindingResult: BindingResult,
    ): Any {
        return Try {
            checkErrors(bindingResult)

            userDeletesItemUseCase.perform(
                UserDeletesItemUseCase.Request(
                    uuid = UUID.fromString(checkNotNull(form.uuid)),
                )
            )

            body(
                HttpStatus.NO_CONTENT,
            )
        }.catch(BadRequestException::class, UserDeletesItemUseCase.ItemIsNotFoundException::class) {
            body(
                HttpStatus.BAD_REQUEST,
                errorMessageList = listOf("不正なリクエストです。"),
            )
        }.rcurl()
    }

}
