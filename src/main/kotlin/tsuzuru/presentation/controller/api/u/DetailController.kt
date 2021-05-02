package tsuzuru.presentation.controller.api.u

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import tsuzuru.common.presentation.controller.impl.AbstractControllerImpl
import tsuzuru.presentation.form.api.u.DetailForm
import tsuzuru.useCase.query.GuestGetsUserActivityQuery
import java.time.LocalDate

@Controller
class DetailController(
    private val guestGetsUserActivityQuery: GuestGetsUserActivityQuery,
) : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.GET], path = ["/api/u/{name}"])
    fun get(
        @Validated form: DetailForm,
        bindingResult: BindingResult,
        model: Model,
    ): Any {
        return Try {
            checkErrors(bindingResult)

            val response: GuestGetsUserActivityQuery.Response = guestGetsUserActivityQuery.perform(
                GuestGetsUserActivityQuery.Request(
                    name = checkNotNull(form.name),
                    range = LocalDate.now().minusMonths(1)..LocalDate.now(),
                    page = form.page,
                    size = form.size,
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
        }.catch(GuestGetsUserActivityQuery.UserIsNotFoundException::class) {
            body(
                HttpStatus.NOT_FOUND,
                errorMessageList = listOf("不正なリクエストです。"),
            )
        }.rcurl()
    }

}
