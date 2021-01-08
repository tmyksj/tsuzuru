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
import tsuzuru.useCase.UserModifiesNameUseCase

@Controller
class NameController(
    private val userModifiesNameUseCase: UserModifiesNameUseCase,
) : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.POST], path = ["/setting/name"])
    fun post(
        @Validated nameForm: NameForm,
        bindingResult: BindingResult,
        model: Model,
        redirectAttributes: RedirectAttributes,
    ): Any {
        return Try {
            checkErrors(bindingResult)

            userModifiesNameUseCase.perform(
                UserModifiesNameUseCase.Request(
                    name = checkNotNull(nameForm.name),
                )
            )

            redirect(
                redirectAttributes, "/setting",
                informationMessageList = listOf("ユーザ名を変更しました。"),
            )
        }.catch(BadRequestException::class, UserModifiesNameUseCase.NameIsAlreadyInUseException::class) {
            model.addAttribute("nameForm", nameForm)
            model.addAttribute("passwordForm", PasswordForm())

            view(
                model, HttpStatus.BAD_REQUEST,
                layout = "layout/default",
                template = "template/setting/index",
                errorMessageList = listOf("入力項目を確認してください。"),
            )
        }.rcurl()
    }

}
