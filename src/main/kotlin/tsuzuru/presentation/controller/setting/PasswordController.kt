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
import tsuzuru.presentation.form.setting.PasswordForm
import tsuzuru.useCase.command.UserModifiesPasswordCommand

@Controller
class PasswordController(
    private val indexController: IndexController,
    private val userModifiesPasswordCommand: UserModifiesPasswordCommand,
) : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.POST], path = ["/setting/password"])
    fun post(
        @Validated form: PasswordForm,
        bindingResult: BindingResult,
        model: Model,
        redirectAttributes: RedirectAttributes,
    ): Any {
        return Try {
            checkErrors(bindingResult)

            userModifiesPasswordCommand.perform(
                UserModifiesPasswordCommand.Request(
                    currentPasswordRaw = checkNotNull(form.currentPassword),
                    newPasswordRaw = checkNotNull(form.newPassword),
                )
            )

            redirect(
                redirectAttributes, "/setting",
                informationMessageList = listOf("パスワードを変更しました。"),
            )
        }.catch(BadRequestException::class, UserModifiesPasswordCommand.PasswordMismatchesException::class) {
            indexController.run { model.addAllForms() }

            view(
                model, HttpStatus.BAD_REQUEST,
                layout = "layout/default",
                template = "template/setting/password",
                errorMessageList = listOf("入力項目を確認してください。"),
            )
        }.rcurl()
    }

}
