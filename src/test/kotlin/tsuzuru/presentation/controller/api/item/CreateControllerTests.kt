package tsuzuru.presentation.controller.api.item

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import tsuzuru.test.ControllerSupport
import tsuzuru.test.PrincipalSupport
import java.util.*

@SpringBootTest
class CreateControllerTests {

    @Autowired
    private lateinit var controllerSupport: ControllerSupport

    @Autowired
    private lateinit var principalSupport: PrincipalSupport

    @Test
    fun post_responds_201() {
        controllerSupport.perform {
            MockMvcRequestBuilders.post("/api/item/create")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.user(principalSupport.principal()))
                .param("body", UUID.randomUUID().toString())
        }.andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    fun post_responds_400_when_params_are_invalid() {
        controllerSupport.perform {
            MockMvcRequestBuilders.post("/api/item/create")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.user(principalSupport.principal()))
                .param("body", "")
        }.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

}
