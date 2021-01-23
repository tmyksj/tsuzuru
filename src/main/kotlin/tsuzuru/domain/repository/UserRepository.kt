package tsuzuru.domain.repository

import tsuzuru.common.domain.exception.DomainException
import tsuzuru.common.domain.query.Page
import tsuzuru.common.domain.query.Pageable
import tsuzuru.common.domain.repository.AbstractRepository
import tsuzuru.domain.entity.UserEntity

interface UserRepository : AbstractRepository<UserEntity> {

    fun existsByRole(role: UserEntity.Role): Boolean

    fun findAll(pageable: Pageable): Page<UserEntity>

    fun findByName(name: String): UserEntity?

    class NameMustBeUniqueException : DomainException()

}
