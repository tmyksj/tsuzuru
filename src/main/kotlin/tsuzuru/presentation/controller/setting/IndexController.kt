package tsuzuru.presentation.controller.setting

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import tsuzuru.common.presentation.controller.impl.AbstractControllerImpl
import tsuzuru.presentation.form.setting.NameForm
import tsuzuru.presentation.form.setting.PasswordForm
import tsuzuru.presentation.form.setting.ProfileForm
import tsuzuru.security.service.SecurityService

@Controller
class IndexController(
    private val securityService: SecurityService,
) : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.GET], path = ["/setting"])
    fun get(
        model: Model,
    ): Any {
        model.addAllForms()

        return view(
            model, HttpStatus.OK,
            layout = "layout/default",
            template = "template/setting/index",
        )
    }

    fun Model.addAllForms(
        nameForm: NameForm = NameForm(
            name = securityService.principal().userEntity.name,
        ),
        passwordForm: PasswordForm = PasswordForm(),
        profileForm: ProfileForm = ProfileForm(
            profileName = securityService.principal().userEntity.profileName,
            scope = securityService.principal().userEntity.scope.toString(),
        ),
    ) {
        addAttribute("nameForm", nameForm)
        addAttribute("passwordForm", passwordForm)
        addAttribute("profileForm", profileForm)
    }

}
