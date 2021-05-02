package tsuzuru.presentation.form.u

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class DetailForm(

    @field:NotBlank
    @field:Pattern(regexp = "[\\w-]+")
    @field:Size(max = 255, min = 6)
    val name: String? = null,

    )
