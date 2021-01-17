package tsuzuru.presentation.form.api.item

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.validation.constraints.Max
import javax.validation.constraints.Min

data class IndexForm(

    @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val page: LocalDateTime = LocalDateTime.now(),

    @field:Max(value = 100)
    @field:Min(value = 1)
    val size: Int = 100,

    )
