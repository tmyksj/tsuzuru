package tsuzuru.presentation.form.api.item

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class DeleteForm(

    @field:NotBlank
    @field:Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")
    val uuid: String? = null,

    )
