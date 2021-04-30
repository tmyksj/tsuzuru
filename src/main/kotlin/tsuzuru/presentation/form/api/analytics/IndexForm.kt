package tsuzuru.presentation.form.api.analytics

import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate

data class IndexForm(

    @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val start: LocalDate = LocalDate.now().minusMonths(1),

    @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    val endInclusive: LocalDate = LocalDate.now(),

    )
