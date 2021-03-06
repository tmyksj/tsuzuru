package tsuzuru.presentation.form.api.item

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class CreateForm(

    @field:NotBlank
    @field:Size(max = 1024, min = 0)
    val body: String? = null,

    )
