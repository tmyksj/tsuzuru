package tsuzuru.presentation.form.signUp

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class IndexForm(

    @field:NotBlank
    @field:Pattern(regexp = "[\\w-]+")
    @field:Size(max = 255, min = 6)
    val name: String? = null,

    @field:NotBlank
    @field:Size(max = 255, min = 8)
    val password: String? = null,

    )
