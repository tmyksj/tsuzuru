package tsuzuru.presentation.form.item

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

data class CreateForm(

    @field:NotBlank
    @field:Size(max = 1024, min = 0)
    var body: String? = null,

    )
