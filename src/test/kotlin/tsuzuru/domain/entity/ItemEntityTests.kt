package tsuzuru.domain.entity

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tsuzuru.test.EntitySupport
import java.util.*

@SpringBootTest
class ItemEntityTests {

    @Autowired
    private lateinit var entitySupport: EntitySupport

    @Test
    fun modifyBody_modifies_body() {
        val body: String = UUID.randomUUID().toString()
        val entity: ItemEntity = entitySupport.itemEntity().modifyBody(body)
        Assertions.assertThat(entity.body).isEqualTo(body)
    }

}
