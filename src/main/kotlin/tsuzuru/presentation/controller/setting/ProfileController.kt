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
import tsuzuru.domain.entity.UserEntity
import tsuzuru.presentation.form.setting.ProfileForm
import tsuzuru.useCase.command.UserModifiesProfileCommand

@Controller
class ProfileController(
    private val indexController: IndexController,
    private val userModifiesProfileCommand: UserModifiesProfileCommand,
) : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.POST], path = ["/setting/profile"])
    fun post(
        @Validated form: ProfileForm,
        bindingResult: BindingResult,
        model: Model,
        redirectAttributes: RedirectAttributes,
    ): Any {
        return Try {
            checkErrors(bindingResult)

            userModifiesProfileCommand.perform(
                UserModifiesProfileCommand.Request(
                    profileName = checkNotNull(form.profileName),
                    scope = UserEntity.Scope.valueOf(checkNotNull(form.scope)),
                )
            )

            redirect(
                redirectAttributes, "/setting",
                informationMessageList = listOf("プロフィールを更新しました。"),
            )
        }.catch(BadRequestException::class) {
            indexController.run { model.addAllForms(profileForm = form) }

            view(
                model, HttpStatus.BAD_REQUEST,
                layout = "layout/default",
                template = "template/setting/profile",
                errorMessageList = listOf("入力項目を確認してください。"),
            )
        }.rcurl()
    }

}
