package tsuzuru.infrastructure.jpa.repository

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import tsuzuru.common.infrastructure.jpa.repository.AbstractRepository
import tsuzuru.infrastructure.jpa.entity.TblItemEntity

@Repository
@Transactional
interface TblItemRepository : AbstractRepository<TblItemEntity, String> {

    fun findByTblUserUuidOrderByWrittenDateAsc(tblUserUuid: String): List<TblItemEntity>

}
