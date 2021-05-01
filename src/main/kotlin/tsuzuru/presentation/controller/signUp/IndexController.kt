package tsuzuru.presentation.controller.signUp

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import tsuzuru.common.presentation.controller.impl.AbstractControllerImpl
import tsuzuru.presentation.form.signUp.IndexForm
import tsuzuru.security.service.SecurityService
import tsuzuru.useCase.command.GuestSignsUpCommand

@Controller
class IndexController(
    private val securityService: SecurityService,
    private val guestSignsUpCommand: GuestSignsUpCommand,
) : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.GET], path = ["/sign-up"])
    fun get(
        model: Model,
        redirectAttributes: RedirectAttributes,
    ): Any {
        if (securityService.isAuthenticated()) {
            return redirect(redirectAttributes, "/")
        }

        model.addAttribute("form", IndexForm())

        return view(
            model, HttpStatus.OK,
            layout = "layout/default",
            template = "template/signUp/index",
        )
    }

    @RequestMapping(method = [RequestMethod.POST], path = ["/sign-up"])
    fun post(
        @Validated form: IndexForm,
        bindingResult: BindingResult,
        model: Model,
        redirectAttributes: RedirectAttributes,
    ): Any {
        if (securityService.isAuthenticated()) {
            return redirect(redirectAttributes, "/")
        }

        return Try {
            checkErrors(bindingResult)

            guestSignsUpCommand.perform(
                GuestSignsUpCommand.Request(
                    name = checkNotNull(form.name),
                    passwordRaw = checkNotNull(form.password),
                )
            )

            redirect(
                redirectAttributes, "/sign-in",
                informationMessageList = listOf("アカウントを作成しました。"),
            )
        }.catch(BadRequestException::class, GuestSignsUpCommand.NameIsAlreadyInUseException::class) {
            model.addAttribute("form", form)

            view(
                model, HttpStatus.BAD_REQUEST,
                layout = "layout/default",
                template = "template/signUp/index",
                errorMessageList = listOf("入力項目を確認してください。"),
            )
        }.rcurl()
    }

}
