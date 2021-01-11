package tsuzuru.presentation.controller.root

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import tsuzuru.common.presentation.controller.impl.AbstractControllerImpl
import tsuzuru.presentation.form.item.CreateForm
import tsuzuru.security.service.SecurityService
import tsuzuru.useCase.UserGetsInformationUseCase

@Controller
class IndexController(
    private val securityService: SecurityService,
    private val userGetsInformationUseCase: UserGetsInformationUseCase,
) : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.GET], path = ["/"])
    fun get(
        model: Model,
    ): Any {
        if (securityService.isAuthenticated()) {
            val response: UserGetsInformationUseCase.Response = userGetsInformationUseCase.perform(
                UserGetsInformationUseCase.Request()
            )

            model.addAttribute("itemCreateForm", CreateForm())
            model.addAttribute("response", response)
        }

        return view(
            model, HttpStatus.OK,
            layout = "layout/default",
            template = "template/root/index",
        )
    }

}
