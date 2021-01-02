package tsuzuru.common.infrastructure.jpa.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import tsuzuru.common.infrastructure.jpa.entity.AbstractEntity

@NoRepositoryBean
interface AbstractRepository<T : AbstractEntity, ID> : JpaRepository<T, ID>
