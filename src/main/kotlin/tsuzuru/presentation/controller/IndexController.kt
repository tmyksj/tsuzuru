package tsuzuru.presentation.controller

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import tsuzuru.common.presentation.controller.impl.AbstractControllerImpl

@Controller
class IndexController : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.GET], path = ["/"])
    fun get(
        model: Model,
    ): Any {
        return view(
            model, HttpStatus.OK,
            layout = "layout/default",
            template = "template/index",
        )
    }

}
