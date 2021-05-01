package tsuzuru.presentation.controller.admin.database

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import tsuzuru.test.ControllerSupport
import tsuzuru.test.PrincipalSupport

@SpringBootTest
class ManagerControllerTests {

    @Autowired
    private lateinit var controllerSupport: ControllerSupport

    @Autowired
    private lateinit var principalSupport: PrincipalSupport

    @Test
    fun exchange_responds_200() {
        controllerSupport.perform {
            MockMvcRequestBuilders.get("/admin/database/manager/")
                .with(SecurityMockMvcRequestPostProcessors.user(principalSupport.principal()))
        }.andExpect(MockMvcResultMatchers.status().isOk)
    }

}
