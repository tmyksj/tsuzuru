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
@Table(name = "tbl_item")
data class TblItemEntity(

    @field:Column(name = "uuid")
    @field:Id
    var uuid: String? = null,

    @field:Column(name = "tbl_user_uuid")
    var tblUserUuid: String? = null,

    @field:Column(name = "body")
    var body: String? = null,

    @field:Column(name = "written_date")
    var writtenDate: LocalDateTime? = null,

    ) : AbstractEntity() {

    @Column(name = "created_date")
    @CreatedDate
    var createdDate: LocalDateTime? = null

    @Column(name = "last_modified_date")
    @LastModifiedDate
    var lastModifiedDate: LocalDateTime? = null

}
