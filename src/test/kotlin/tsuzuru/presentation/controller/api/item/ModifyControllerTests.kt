package tsuzuru.presentation.controller.api.item

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import tsuzuru.security.principal.Principal
import tsuzuru.test.ControllerSupport
import tsuzuru.test.EntitySupport
import tsuzuru.test.PrincipalSupport
import java.util.*

@SpringBootTest
class ModifyControllerTests {

    @Autowired
    private lateinit var controllerSupport: ControllerSupport

    @Autowired
    private lateinit var entitySupport: EntitySupport

    @Autowired
    private lateinit var principalSupport: PrincipalSupport

    @Test
    fun post_responds_200() {
        controllerSupport.perform {
            val principal: Principal = principalSupport.principal()

            MockMvcRequestBuilders.post("/api/item/${entitySupport.itemEntity(userEntity = principal.userEntity).uuid}/modify")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.user(principal))
                .param("body", UUID.randomUUID().toString())
        }.andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun post_responds_400_when_item_does_not_exist() {
        controllerSupport.perform {
            MockMvcRequestBuilders.post("/api/item/${UUID.randomUUID()}/modify")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.user(principalSupport.principal()))
                .param("body", UUID.randomUUID().toString())
        }.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun post_responds_400_when_item_is_owned_by_another_user() {
        controllerSupport.perform {
            MockMvcRequestBuilders.post("/api/item/${entitySupport.itemEntity().uuid}/modify")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.user(principalSupport.principal()))
                .param("body", UUID.randomUUID().toString())
        }.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun post_responds_400_when_params_are_invalid() {
        controllerSupport.perform {
            val principal: Principal = principalSupport.principal()

            MockMvcRequestBuilders.post("/api/item/${entitySupport.itemEntity(userEntity = principal.userEntity).uuid}/modify")
                .with(SecurityMockMvcRequestPostProcessors.csrf())
                .with(SecurityMockMvcRequestPostProcessors.user(principal))
                .param("body", "")
        }.andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

}
