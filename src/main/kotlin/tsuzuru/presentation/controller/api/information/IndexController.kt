package tsuzuru.presentation.controller.api.information

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import tsuzuru.common.presentation.controller.impl.AbstractControllerImpl
import tsuzuru.useCase.UserGetsInformationUseCase

@RestController
class IndexController(
    private val userGetsInformationUseCase: UserGetsInformationUseCase,
) : AbstractControllerImpl() {

    @RequestMapping(method = [RequestMethod.GET], path = ["/api/information"])
    fun get(): Any {
        val response: UserGetsInformationUseCase.Response = userGetsInformationUseCase.perform(
            UserGetsInformationUseCase.Request()
        )

        return body(
            HttpStatus.OK, response,
        )
    }

}
