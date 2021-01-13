package tsuzuru.presentation.controller.api.information

import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import tsuzuru.common.presentation.controller.impl.AbstractControllerImpl
import tsuzuru.presentation.form.api.information.IndexForm
import tsuzuru.useCase.UserGetsInformationUseCase

@RestController
class IndexController(
    private val userGetsInformationUseCase: UserGetsInformationUseCase,
) : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.GET], path = ["/api/information"])
    fun get(
        @Validated form: IndexForm,
        bindingResult: BindingResult,
    ): Any {
        return Try {
            checkErrors(bindingResult)

            val response: UserGetsInformationUseCase.Response = userGetsInformationUseCase.perform(
                UserGetsInformationUseCase.Request()
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
