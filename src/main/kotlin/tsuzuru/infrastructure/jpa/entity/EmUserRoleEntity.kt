package tsuzuru.infrastructure.jpa.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import tsuzuru.common.infrastructure.jpa.entity.AbstractEntity
import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "em_user_role")
data class EmUserRoleEntity(

    @field:Column(name = "value")
    @field:Id
    var value: String? = null,

    ) : AbstractEntity() {

    @Column(name = "created_date")
    @CreatedDate
    var createdDate: LocalDateTime? = null

    @Column(name = "last_modified_date")
    @LastModifiedDate
    var lastModifiedDate: LocalDateTime? = null

}
