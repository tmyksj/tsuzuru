package tsuzuru.presentation.controller.root

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import tsuzuru.test.ControllerSupport

@SpringBootTest
class IndexControllerTests {

    @Autowired
    private lateinit var controllerSupport: ControllerSupport

    @Test
    fun get_responds_200() {
        controllerSupport.perform {
            MockMvcRequestBuilders.get("/")
        }.andExpect(MockMvcResultMatchers.status().isOk)
    }

}
