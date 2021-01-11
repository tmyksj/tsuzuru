package tsuzuru.presentation.controller.item

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import tsuzuru.common.presentation.controller.impl.AbstractControllerImpl
import tsuzuru.presentation.form.item.CreateForm
import tsuzuru.useCase.UserCreatesItemUseCase
import tsuzuru.useCase.UserGetsInformationUseCase

@Controller
class CreateController(
    private val userCreatesItemUseCase: UserCreatesItemUseCase,
    private val userGetsInformationUseCase: UserGetsInformationUseCase,
) : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.POST], path = ["/item/create"])
    fun post(
        @Validated form: CreateForm,
        bindingResult: BindingResult,
        model: Model,
        redirectAttributes: RedirectAttributes,
    ): Any {
        return Try {
            checkErrors(bindingResult)

            userCreatesItemUseCase.perform(
                UserCreatesItemUseCase.Request(
                    body = checkNotNull(form.body),
                )
            )

            redirect(
                redirectAttributes, "/",
                informationMessageList = listOf("作成しました。"),
            )
        }.catch(BadRequestException::class) {
            val response: UserGetsInformationUseCase.Response = userGetsInformationUseCase.perform(
                UserGetsInformationUseCase.Request()
            )

            model.addAttribute("itemCreateForm", form)
            model.addAttribute("response", response)

            view(
                model, HttpStatus.BAD_REQUEST,
                layout = "layout/default",
                template = "template/item/create",
                errorMessageList = listOf("入力項目を確認してください。"),
            )
        }.rcurl()
    }

}
