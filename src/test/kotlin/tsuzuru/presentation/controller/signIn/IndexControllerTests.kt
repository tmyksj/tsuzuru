package tsuzuru.presentation.controller.signIn

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import tsuzuru.test.ControllerSupport
import tsuzuru.test.PrincipalSupport

@SpringBootTest
class IndexControllerTests {

    @Autowired
    private lateinit var controllerSupport: ControllerSupport

    @Autowired
    private lateinit var principalSupport: PrincipalSupport

    @Test
    fun get_responds_200() {
        controllerSupport.perform {
            MockMvcRequestBuilders.get("/sign-in")
        }.andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun get_responds_302_when_user_signed_in() {
        controllerSupport.perform {
            MockMvcRequestBuilders.get("/sign-in")
                .with(SecurityMockMvcRequestPostProcessors.user(principalSupport.principal()))
        }.andExpect(MockMvcResultMatchers.status().isFound)
    }

}
