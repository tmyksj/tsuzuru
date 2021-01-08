package tsuzuru.presentation.controller.signIn

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import tsuzuru.common.presentation.controller.impl.AbstractControllerImpl
import tsuzuru.security.service.SecurityService

@Controller
class IndexController(
    private val securityService: SecurityService,
) : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.GET], path = ["/sign-in"])
    fun get(
        model: Model,
        redirectAttributes: RedirectAttributes,
    ): Any {
        if (securityService.isAuthenticated()) {
            return redirect(redirectAttributes, "/")
        }

        return view(
            model, HttpStatus.OK,
            layout = "layout/default",
            template = "template/signIn/index",
        )
    }

}
