package tsuzuru.presentation.controller.analytics

import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import tsuzuru.common.presentation.controller.impl.AbstractControllerImpl
import tsuzuru.presentation.form.analytics.IndexForm
import tsuzuru.useCase.query.UserGetsAnalyticsQuery

@RestController
class IndexController(
    private val userGetsAnalyticsQuery: UserGetsAnalyticsQuery,
) : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.GET], path = ["/analytics"])
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

            model.addAttribute("form", form)
            model.addAttribute("response", response)

            view(
                model, HttpStatus.OK,
                layout = "layout/default",
                template = "template/analytics/index",
            )
        }.catch(BadRequestException::class) {
            val validForm = IndexForm()

            val response: UserGetsAnalyticsQuery.Response = userGetsAnalyticsQuery.perform(
                UserGetsAnalyticsQuery.Request(
                    range = validForm.start..validForm.endInclusive
                )
            )

            model.addAttribute("form", validForm)
            model.addAttribute("response", response)

            view(
                model, HttpStatus.OK,
                layout = "layout/default",
                template = "template/analytics/index",
                warningMessageList = listOf("パラメタが不正です。"),
            )
        }.rcurl()
    }

}
