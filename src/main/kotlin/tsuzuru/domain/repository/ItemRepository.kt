package tsuzuru.domain.repository

import tsuzuru.common.domain.query.Page
import tsuzuru.common.domain.query.Pageable
import tsuzuru.common.domain.repository.AbstractRepository
import tsuzuru.domain.entity.ItemEntity

interface ItemRepository : AbstractRepository<ItemEntity> {

    fun findAll(pageable: Pageable): Page<ItemEntity>

}
