package tsuzuru.presentation.controller.signUp

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
class IndexControllerTests {

    @Autowired
    private lateinit var controllerSupport: ControllerSupport

    @Autowired
    private lateinit var principalSupport: PrincipalSupport

    @Test
    fun get_responds_200() {
        controllerSupport.perform {
            MockMvcRequestBuilders.get("/sign-up")
        }.andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun get_responds_302_when_user_signed_in() {
        controllerSupport.perform {
            MockMvcRequestBuilders.get("/sign-up")
                .with(SecurityMockMvcRequestPostProcessors.user(principalSupport.principal()))
        }.andExpect(MockMvcResultMatchers.status().isFound)
    }

    @Test
    fun post_responds_302() {
        controllerSupport.perform {
            MockMvcRequestBuilders.post("/sign-up")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("name", UUID.randomUUID().toString())
                .param("password", "passwordRaw")
        }.andExpect(MockMvcResultMatchers.status().isFound)
    }

    @Test
    fun post_responds_302_when_user_signed_in() {
        controllerSupport.perform {
            MockMvcRequestBuilders.post("/sign-up")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.user(principalSupport.principal()))
                .param("name", UUID.randomUUID().toString())
                .param("password", "passwordRaw")
        }.andExpect(MockMvcResultMatchers.status().isFound)
    }

    @Test
    fun post_responds_400_when_params_are_invalid() {
        controllerSupport.perform {
            MockMvcRequestBuilders.post("/sign-up")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .param("name", "[invalid]name")
                .param("password", "passwordRaw")
        }.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

}
