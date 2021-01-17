package tsuzuru.presentation.controller.api.item

import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import tsuzuru.common.presentation.controller.impl.AbstractControllerImpl
import tsuzuru.presentation.form.api.item.IndexForm
import tsuzuru.useCase.UserGetsItemsUseCase

@RestController
class IndexController(
    private val userGetsItemsUseCase: UserGetsItemsUseCase,
) : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.GET], path = ["/api/item"])
    fun get(
        @Validated form: IndexForm,
        bindingResult: BindingResult,
    ): Any {
        return Try {
            checkErrors(bindingResult)

            val response: UserGetsItemsUseCase.Response = userGetsItemsUseCase.perform(
                UserGetsItemsUseCase.Request(
                    page = checkNotNull(form.page),
                    size = checkNotNull(form.size),
                )
            )

            body(
                HttpStatus.OK, response,
            )
        }.catch(BadRequestException::class) {
            body(
                HttpStatus.BAD_REQUEST,
                errorMessageList = listOf("不正なリクエストです。"),
            )
        }.rcurl()
    }

}
