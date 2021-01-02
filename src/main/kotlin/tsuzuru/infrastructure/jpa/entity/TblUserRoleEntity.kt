package tsuzuru.infrastructure.jpa.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import tsuzuru.common.infrastructure.jpa.entity.AbstractEntity
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "tbl_user_role")
data class TblUserRoleEntity(

    @field:EmbeddedId
    var id: Id? = null,

    ) : AbstractEntity() {

    @Column(name = "created_date")
    @CreatedDate
    var createdDate: LocalDateTime? = null

    @Column(name = "last_modified_date")
    @LastModifiedDate
    var lastModifiedDate: LocalDateTime? = null

    @Embeddable
    data class Id(

        @field:Column(name = "tbl_user_uuid")
        var tblUserUuid: String? = null,

        @field:Column(name = "em_user_role_value")
        var emUserRoleValue: String? = null,

        ) : Serializable

}
