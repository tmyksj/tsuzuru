package tsuzuru.infrastructure.jpa.repository

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import tsuzuru.common.infrastructure.jpa.repository.AbstractRepository
import tsuzuru.infrastructure.jpa.entity.EmUserRoleEntity

@Repository
@Transactional
interface EmUserRoleRepository : AbstractRepository<EmUserRoleEntity, String>
