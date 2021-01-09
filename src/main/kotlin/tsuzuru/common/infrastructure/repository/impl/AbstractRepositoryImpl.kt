package tsuzuru.common.infrastructure.repository.impl

import tsuzuru.common.infrastructure.jpa.entity.AbstractEntity
import tsuzuru.common.infrastructure.jpa.repository.AbstractRepository
import javax.annotation.PostConstruct
import kotlin.reflect.full.createInstance

abstract class AbstractRepositoryImpl {

    @PostConstruct
    abstract fun initialize()

    inline fun <E, reified T : AbstractEntity, ID> save(
        repository: AbstractRepository<T, ID>,
        entity: E,
        tblEntity: T?,
        tblEntitySetter: T.(E) -> Unit,
    ): T {
        return repository.save((tblEntity ?: T::class.createInstance()).apply { tblEntitySetter(entity) })
    }

    inline fun <E, reified T : AbstractEntity, ID, Key> save(
        repository: AbstractRepository<T, ID>,
        entityList: List<E>,
        entityKeySelector: (E) -> Key,
        tblEntityList: List<T>,
        tblEntityKeySelector: T.() -> Key,
        tblEntitySetter: T.(E) -> Unit,
    ) {
        val entityMap: Map<Key, E> = entityList.associateBy(entityKeySelector)
        val tblEntityMap: Map<Key, T> = tblEntityList.associateBy(tblEntityKeySelector)

        repository.deleteAll(tblEntityMap.filter { entityMap[it.key] == null }.values)
        repository.saveAll(entityMap.map {
            (tblEntityMap[it.key] ?: T::class.createInstance()).apply { tblEntitySetter(it.value) }
        })
    }

}
