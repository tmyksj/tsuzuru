package tsuzuru.infrastructure.jpa.repository

import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import tsuzuru.common.infrastructure.jpa.repository.AbstractRepository
import tsuzuru.infrastructure.jpa.entity.TblItemEntity
import java.time.LocalDateTime

@Repository
@Transactional
interface TblItemRepository : AbstractRepository<TblItemEntity, String> {

    fun findByTblUserUuidAndWrittenDateLessThanEqualOrderByWrittenDateDesc(
        tblUserUuid: String,
        writtenDate: LocalDateTime,
        pageable: Pageable,
    ): List<TblItemEntity>

}
