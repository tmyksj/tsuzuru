package tsuzuru.presentation.form.setting

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class ProfileForm(

    @field:NotBlank
    @field:Size(max = 255, min = 1)
    val profileName: String? = null,

    @field:NotBlank
    @field:Pattern(regexp = "Private|Public")
    val scope: String? = null,

    )
