package tsuzuru.presentation.controller.admin.database

import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.RequestEntity
import org.springframework.stereotype.Controller
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.client.RestTemplate
import org.springframework.web.multipart.MultipartHttpServletRequest
import java.net.URI
import javax.servlet.http.HttpServletRequest

@Controller
class ManagerController(
    private val restTemplate: RestTemplate,
) {

    @RequestMapping(method = [RequestMethod.GET, RequestMethod.POST], path = ["/admin/database/manager/**"])
    fun exchange(
        httpServletRequest: HttpServletRequest,
    ): Any {
        return restTemplate.exchange(
            RequestEntity(
                LinkedMultiValueMap<String, Any>().apply {
                    httpServletRequest.parameterMap.forEach {
                        addAll(it.key, it.value.toList())
                    }
                    if (httpServletRequest is MultipartHttpServletRequest) {
                        httpServletRequest.multiFileMap.forEach { entry ->
                            entry.value.forEach {
                                add(entry.key, object : ByteArrayResource(it.bytes) {
                                    override fun getFilename(): String? {
                                        return it.originalFilename
                                    }
                                })
                            }
                        }
                    }
                },
                HttpHeaders().apply {
                    httpServletRequest.headerNames.toList().forEach {
                        addAll(it, httpServletRequest.getHeaders(it).toList())
                    }
                },
                HttpMethod.resolve(httpServletRequest.method),
                URI(
                    "http",
                    "db-manager",
                    httpServletRequest.requestURI.substring(23),
                    httpServletRequest.queryString,
                    null,
                ),
            ),
            ByteArray::class.java,
        )
    }

}
