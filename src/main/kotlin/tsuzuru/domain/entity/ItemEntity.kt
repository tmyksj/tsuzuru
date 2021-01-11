package tsuzuru.domain.entity

import tsuzuru.common.domain.entity.AbstractEntity
import java.time.LocalDateTime
import java.util.*

data class ItemEntity(
    val uuid: UUID,
    val userEntity: UserEntity,
    val body: String,
    val writtenDate: LocalDateTime,
) : AbstractEntity() {

    fun modifyBody(body: String): ItemEntity {
        return copy(body = body)
    }

    companion object {

        fun build(
            uuid: UUID = UUID.randomUUID(),
            userEntity: UserEntity,
            body: String,
            writtenDate: LocalDateTime = LocalDateTime.now(),
        ): ItemEntity {
            return ItemEntity(
                uuid = uuid,
                userEntity = userEntity,
                body = body,
                writtenDate = writtenDate,
            )
        }

    }

}
