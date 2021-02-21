package tsuzuru.presentation.controller.admin.proxy

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.client.RestTemplate
import java.net.URI
import javax.servlet.http.HttpServletRequest

@Controller
class PhpmyadminController(
    private val restTemplate: RestTemplate,
) {

    @RequestMapping(method = [RequestMethod.GET, RequestMethod.POST], path = ["/admin/proxy/phpmyadmin/**"])
    fun exchange(
        httpServletRequest: HttpServletRequest,
        @RequestBody(required = false) requestBody: ByteArray?,
    ): Any {
        return restTemplate.exchange(
            RequestEntity(
                requestBody,
                HttpHeaders().apply {
                    httpServletRequest.headerNames.toList().forEach {
                        addAll(it, httpServletRequest.getHeaders(it).toList())
                    }
                },
                HttpMethod.resolve(httpServletRequest.method),
                URI(
                    "http",
                    "phpmyadmin",
                    httpServletRequest.requestURI.substring(23),
                    httpServletRequest.queryString,
                    null,
                ),
            ),
            ByteArray::class.java,
        )
    }

}
