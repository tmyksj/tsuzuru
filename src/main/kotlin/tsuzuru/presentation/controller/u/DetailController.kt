package tsuzuru.presentation.controller.u

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import tsuzuru.common.presentation.controller.impl.AbstractControllerImpl
import tsuzuru.presentation.form.u.DetailForm
import tsuzuru.useCase.query.GuestGetsUserActivityQuery
import java.time.LocalDate
import java.time.LocalDateTime

@Controller
class DetailController(
    private val guestGetsUserActivityQuery: GuestGetsUserActivityQuery,
) : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.GET], path = ["/u/{name}"])
    fun get(
        @Validated form: DetailForm,
        bindingResult: BindingResult,
        model: Model,
    ): Any {
        return Try {
            checkErrors(bindingResult)

            guestGetsUserActivityQuery.perform(
                GuestGetsUserActivityQuery.Request(
                    name = checkNotNull(form.name),
                    range = LocalDate.now().minusMonths(1)..LocalDate.now(),
                    page = LocalDateTime.now(),
                    size = 100,
                )
            )

            view(
                model, HttpStatus.OK,
                layout = "layout/default",
                template = "template/u/detail",
            )
        }.catch(BadRequestException::class) {
            view(
                model, HttpStatus.BAD_REQUEST,
                layout = "layout/default",
                template = "template/u/detail",
            )
        }.catch(GuestGetsUserActivityQuery.UserIsNotFoundException::class) {
            view(
                model, HttpStatus.NOT_FOUND,
                layout = "layout/default",
                template = "template/u/detail",
            )
        }.rcurl()
    }

}
