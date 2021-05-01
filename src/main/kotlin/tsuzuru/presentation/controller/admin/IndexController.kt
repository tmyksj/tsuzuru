package tsuzuru.presentation.controller.admin

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import tsuzuru.common.presentation.controller.impl.AbstractControllerImpl
import tsuzuru.useCase.query.AdminGetsStatisticsQuery

@Controller
class IndexController(
    private val adminGetsStatisticsQuery: AdminGetsStatisticsQuery,
) : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.GET], path = ["/admin"])
    fun get(
        model: Model,
    ): Any {
        val response: AdminGetsStatisticsQuery.Response = adminGetsStatisticsQuery.perform(
            AdminGetsStatisticsQuery.Request()
        )

        model.addAttribute("response", response)

        return view(
            model, HttpStatus.OK,
            layout = "layout/admin",
            template = "template/admin/index",
        )
    }

}
