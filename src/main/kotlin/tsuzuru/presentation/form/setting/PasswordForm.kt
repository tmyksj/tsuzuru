package tsuzuru.presentation.form.setting

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class PasswordForm(

    @field:NotBlank
    @field:Size(max = 255, min = 8)
    val currentPassword: String? = null,

    @field:NotBlank
    @field:Size(max = 255, min = 8)
    val newPassword: String? = null,

    )
