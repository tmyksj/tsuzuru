package tsuzuru.presentation.controller.admin.database

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import tsuzuru.common.presentation.controller.impl.AbstractControllerImpl

@Controller
class IndexController : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.GET], path = ["/admin/database"])
    fun get(
        model: Model,
    ): Any {
        return view(
            model, HttpStatus.OK,
            layout = "layout/admin",
            template = "template/admin/database/index",
        )
    }

}
