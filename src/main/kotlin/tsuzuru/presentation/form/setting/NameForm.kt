package tsuzuru.presentation.form.setting

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class NameForm(

    @field:NotBlank
    @field:Pattern(regexp = "[\\w-]+")
    @field:Size(max = 255, min = 6)
    var name: String? = null,

    )
