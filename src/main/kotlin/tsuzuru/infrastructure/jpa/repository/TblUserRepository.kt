package tsuzuru.infrastructure.jpa.repository

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import tsuzuru.common.infrastructure.jpa.repository.AbstractRepository
import tsuzuru.infrastructure.jpa.entity.TblUserEntity

@Repository
@Transactional
interface TblUserRepository : AbstractRepository<TblUserEntity, String> {

    fun findByName(name: String): TblUserEntity?

}
