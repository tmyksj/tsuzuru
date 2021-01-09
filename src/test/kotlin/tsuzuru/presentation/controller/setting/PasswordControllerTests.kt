package tsuzuru.presentation.controller.setting

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import tsuzuru.test.ControllerSupport
import tsuzuru.test.PrincipalSupport

@SpringBootTest
class PasswordControllerTests {

    @Autowired
    private lateinit var controllerSupport: ControllerSupport

    @Autowired
    private lateinit var principalSupport: PrincipalSupport

    @Test
    fun post_responds_302() {
        controllerSupport.perform {
            MockMvcRequestBuilders.post("/setting/password")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.user(principalSupport.principal()))
                .param("currentPassword", "passwordRaw")
                .param("newPassword", "newPasswordRaw")
        }.andExpect(MockMvcResultMatchers.status().isFound)
    }

    @Test
    fun post_responds_400_when_params_are_invalid() {
        controllerSupport.perform {
            MockMvcRequestBuilders.post("/setting/password")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.user(principalSupport.principal()))
                .param("currentPassword", "wrongPasswordRaw")
                .param("newPassword", "newPasswordRaw")
        }.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

}
