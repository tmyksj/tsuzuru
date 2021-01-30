package tsuzuru.common.domain.repository

import tsuzuru.common.domain.entity.AbstractEntity
import java.util.*

interface AbstractRepository<T : AbstractEntity> {

    fun delete(entity: T)

    fun findByUuid(uuid: UUID): T?

    fun save(entity: T): T

}
