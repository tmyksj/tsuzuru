package tsuzuru.infrastructure.jpa.repository

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import tsuzuru.common.infrastructure.jpa.repository.AbstractRepository
import tsuzuru.infrastructure.jpa.entity.TblUserRoleEntity

@Repository
@Transactional
interface TblUserRoleRepository : AbstractRepository<TblUserRoleEntity, TblUserRoleEntity.Id> {

    fun findByIdTblUserUuid(tblUserUuid: String): List<TblUserRoleEntity>

    fun findByIdTblUserUuidIn(tblUserUuidList: List<String>): List<TblUserRoleEntity>

}
