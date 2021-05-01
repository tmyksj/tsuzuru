package tsuzuru.presentation.controller.api.item

import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import tsuzuru.common.presentation.controller.impl.AbstractControllerImpl
import tsuzuru.presentation.form.api.item.ModifyForm
import tsuzuru.useCase.command.UserModifiesItemCommand
import java.util.*

@RestController
class ModifyController(
    private val userModifiesItemCommand: UserModifiesItemCommand,
) : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.POST], path = ["/api/item/{uuid}/modify"])
    fun post(
        @Validated form: ModifyForm,
        bindingResult: BindingResult,
    ): Any {
        return Try {
            checkErrors(bindingResult)

            userModifiesItemCommand.perform(
                UserModifiesItemCommand.Request(
                    uuid = UUID.fromString(checkNotNull(form.uuid)),
                    body = checkNotNull(form.body),
                )
            )

            body(
                HttpStatus.OK,
            )
        }.catch(BadRequestException::class, UserModifiesItemCommand.ItemIsNotFoundException::class) {
            body(
                HttpStatus.BAD_REQUEST,
                errorMessageList = listOf("不正なリクエストです。"),
            )
        }.rcurl()
    }

}
