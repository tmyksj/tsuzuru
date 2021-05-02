package tsuzuru.presentation.form.api.u

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDateTime
import javax.validation.constraints.*

data class DetailForm(

    @field:NotBlank
    @field:Pattern(regexp = "[\\w-]+")
    @field:Size(max = 255, min = 6)
    val name: String? = null,

    @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val page: LocalDateTime = LocalDateTime.now(),

    @field:Max(value = 100)
    @field:Min(value = 1)
    val size: Int = 100,

    )
