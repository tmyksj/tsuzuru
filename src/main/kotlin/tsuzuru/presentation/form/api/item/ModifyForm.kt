package tsuzuru.presentation.form.api.item

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class ModifyForm(

    @field:NotBlank
    @field:Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")
    val uuid: String? = null,

    @field:NotBlank
    @field:Size(max = 1024, min = 0)
    val body: String? = null,

    )
