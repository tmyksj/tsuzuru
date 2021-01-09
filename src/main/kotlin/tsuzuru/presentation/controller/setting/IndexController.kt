package tsuzuru.presentation.controller.setting

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import tsuzuru.common.presentation.controller.impl.AbstractControllerImpl
import tsuzuru.presentation.form.setting.NameForm
import tsuzuru.presentation.form.setting.PasswordForm
import tsuzuru.security.principal.Principal
import tsuzuru.security.service.SecurityService

@Controller
class IndexController(
    private val securityService: SecurityService,
) : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.GET], path = ["/setting"])
    fun get(
        model: Model,
    ): Any {
        val principal: Principal = securityService.principal()

        model.addAttribute("nameForm", NameForm(name = principal.userEntity.name))
        model.addAttribute("passwordForm", PasswordForm())

        return view(
            model, HttpStatus.OK,
            layout = "layout/default",
            template = "template/setting/index",
        )
    }

}
