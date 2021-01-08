package tsuzuru.test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestComponent
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.RequestBuilder
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import javax.servlet.Filter

@TestComponent
class ControllerSupport {

    @Autowired
    lateinit var webApplicationContext: WebApplicationContext

    @Autowired
    lateinit var springSecurityFilterChain: Filter

    fun perform(block: () -> RequestBuilder): ResultActions {
        val mockMvc: MockMvc = MockMvcBuilders
            .webAppContextSetup(webApplicationContext)
            .addFilters<DefaultMockMvcBuilder>(springSecurityFilterChain)
            .build()
        return mockMvc.perform(block())
    }

}
