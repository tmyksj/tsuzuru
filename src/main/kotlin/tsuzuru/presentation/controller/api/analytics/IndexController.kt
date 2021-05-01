package tsuzuru.presentation.controller.api.analytics

import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import tsuzuru.common.presentation.controller.impl.AbstractControllerImpl
import tsuzuru.presentation.form.api.analytics.IndexForm
import tsuzuru.useCase.query.UserGetsAnalyticsQuery

@RestController
class IndexController(
    private val userGetsAnalyticsQuery: UserGetsAnalyticsQuery,
) : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.GET], path = ["/api/analytics"])
    fun get(
        @Validated form: IndexForm,
        bindingResult: BindingResult,
        model: Model,
    ): Any {
        return Try {
            checkErrors(bindingResult)
            if (form.start > form.endInclusive) {
                throw BadRequestException()
            }

            val response: UserGetsAnalyticsQuery.Response = userGetsAnalyticsQuery.perform(
                UserGetsAnalyticsQuery.Request(
                    range = form.start..form.endInclusive,
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
