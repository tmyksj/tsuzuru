package tsuzuru.presentation.controller.api.item

import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import tsuzuru.common.presentation.controller.impl.AbstractControllerImpl
import tsuzuru.presentation.form.api.item.CreateForm
import tsuzuru.useCase.UserCreatesItemUseCase

@RestController
class CreateController(
    private val userCreatesItemUseCase: UserCreatesItemUseCase,
) : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.POST], path = ["/api/item/create"])
    fun post(
        @Validated form: CreateForm,
        bindingResult: BindingResult,
    ): Any {
        return Try {
            checkErrors(bindingResult)

            userCreatesItemUseCase.perform(
                UserCreatesItemUseCase.Request(
                    body = checkNotNull(form.body),
                )
            )

            body(
                HttpStatus.CREATED,
            )
        }.catch(BadRequestException::class) {
            body(
                HttpStatus.BAD_REQUEST,
                errorMessageList = listOf("不正なリクエストです。"),
            )
        }.rcurl()
    }

}
