package tsuzuru.presentation.controller.u

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import tsuzuru.domain.entity.UserEntity
import tsuzuru.test.ControllerSupport
import tsuzuru.test.EntitySupport
import java.util.*

@SpringBootTest
class DetailControllerTests {

    @Autowired
    private lateinit var controllerSupport: ControllerSupport

    @Autowired
    private lateinit var entitySupport: EntitySupport

    @Test
    fun get_responds_200() {
        controllerSupport.perform {
            MockMvcRequestBuilders.get("/u/${entitySupport.userEntity(scope = UserEntity.Scope.Public).name}")
        }.andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun get_responds_404_when_user_does_not_exist() {
        controllerSupport.perform {
            MockMvcRequestBuilders.get("/u/${UUID.randomUUID()}")
        }.andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun get_responds_404_when_user_sets_scope_to_private() {
        controllerSupport.perform {
            MockMvcRequestBuilders.get("/u/${entitySupport.userEntity(scope = UserEntity.Scope.Private).name}")
        }.andExpect(MockMvcResultMatchers.status().isNotFound)
    }

}
