package tsuzuru.domain.repository

import tsuzuru.common.domain.exception.DomainException
import tsuzuru.common.domain.repository.AbstractRepository
import tsuzuru.domain.entity.UserEntity

interface UserRepository : AbstractRepository<UserEntity> {

    fun existsByRole(role: UserEntity.Role): Boolean

    fun findByName(name: String): UserEntity?

    class NameMustBeUniqueException : DomainException()

}
