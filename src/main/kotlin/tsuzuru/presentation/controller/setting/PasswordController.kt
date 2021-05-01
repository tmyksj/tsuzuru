package tsuzuru.presentation.controller.setting

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import tsuzuru.common.presentation.controller.impl.AbstractControllerImpl
import tsuzuru.presentation.form.setting.NameForm
import tsuzuru.presentation.form.setting.PasswordForm
import tsuzuru.security.principal.Principal
import tsuzuru.security.service.SecurityService
import tsuzuru.useCase.command.UserModifiesPasswordCommand

@Controller
class PasswordController(
    private val securityService: SecurityService,
    private val userModifiesPasswordCommand: UserModifiesPasswordCommand,
) : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.POST], path = ["/setting/password"])
    fun post(
        @Validated passwordForm: PasswordForm,
        bindingResult: BindingResult,
        model: Model,
        redirectAttributes: RedirectAttributes,
    ): Any {
        return Try {
            checkErrors(bindingResult)

            userModifiesPasswordCommand.perform(
                UserModifiesPasswordCommand.Request(
                    currentPasswordRaw = checkNotNull(passwordForm.currentPassword),
                    newPasswordRaw = checkNotNull(passwordForm.newPassword),
                )
            )

            redirect(
                redirectAttributes, "/setting",
                informationMessageList = listOf("パスワードを変更しました。"),
            )
        }.catch(BadRequestException::class, UserModifiesPasswordCommand.PasswordMismatchesException::class) {
            val principal: Principal = securityService.principal()

            model.addAttribute("nameForm", NameForm(name = principal.userEntity.name))
            model.addAttribute("passwordForm", PasswordForm())

            view(
                model, HttpStatus.BAD_REQUEST,
                layout = "layout/default",
                template = "template/setting/password",
                errorMessageList = listOf("入力項目を確認してください。"),
            )
        }.rcurl()
    }

}
