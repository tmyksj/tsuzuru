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
import tsuzuru.useCase.command.UserModifiesNameCommand

@Controller
class NameController(
    private val indexController: IndexController,
    private val userModifiesNameCommand: UserModifiesNameCommand,
) : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.POST], path = ["/setting/name"])
    fun post(
        @Validated form: NameForm,
        bindingResult: BindingResult,
        model: Model,
        redirectAttributes: RedirectAttributes,
    ): Any {
        return Try {
            checkErrors(bindingResult)

            userModifiesNameCommand.perform(
                UserModifiesNameCommand.Request(
                    name = checkNotNull(form.name),
                )
            )

            redirect(
                redirectAttributes, "/setting",
                informationMessageList = listOf("ユーザ名を変更しました。"),
            )
        }.catch(BadRequestException::class, UserModifiesNameCommand.NameIsAlreadyInUseException::class) {
            indexController.run { model.addAllForms(nameForm = form) }

            view(
                model, HttpStatus.BAD_REQUEST,
                layout = "layout/default",
                template = "template/setting/name",
                errorMessageList = listOf("入力項目を確認してください。"),
            )
        }.rcurl()
    }

}
