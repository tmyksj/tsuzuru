package tsuzuru.domain.repository

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import tsuzuru.common.domain.query.Page
import tsuzuru.common.domain.query.Pageable
import tsuzuru.domain.entity.ItemEntity
import tsuzuru.test.EntitySupport
import java.util.*

@SpringBootTest
class ItemRepositoryTests {

    @Autowired
    private lateinit var itemRepository: ItemRepository

    @Autowired
    private lateinit var entitySupport: EntitySupport

    @Test
    fun delete_deletes_entity() {
        val itemEntity: ItemEntity = entitySupport.itemEntity()
        itemRepository.delete(itemEntity)

        val entity: ItemEntity? = itemRepository.findByUuid(itemEntity.uuid)
        Assertions.assertThat(entity).isNull()
    }

    @Test
    fun findAll_returns_entities() {
        val page: Page<ItemEntity> = itemRepository.findAll(Pageable(0, 10))
        Assertions.assertThat(page.entityList).hasSizeLessThanOrEqualTo(10)
    }

    @Test
    fun findByUuid_returns_entity() {
        val itemEntity: ItemEntity = entitySupport.itemEntity()
        val entity: ItemEntity? = itemRepository.findByUuid(itemEntity.uuid)
        Assertions.assertThat(entity).isEqualTo(itemEntity)
    }

    @Test
    fun findByUuid_returns_null_when_entity_does_not_exist() {
        val entity: ItemEntity? = itemRepository.findByUuid(UUID.randomUUID())
        Assertions.assertThat(entity).isNull()
    }

}
