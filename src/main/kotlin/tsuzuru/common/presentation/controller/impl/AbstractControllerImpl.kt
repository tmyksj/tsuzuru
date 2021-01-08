package tsuzuru.common.presentation.controller.impl

import org.springframework.http.HttpStatus
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.View
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import tsuzuru.common.presentation.exception.PresentationException
import kotlin.reflect.KClass

abstract class AbstractControllerImpl {

    fun checkErrors(bindingResult: BindingResult) {
        if (bindingResult.hasErrors()) {
            throw BadRequestException()
        }
    }

    fun redirect(
        redirectAttributes: RedirectAttributes,
        location: String,
        informationMessageList: List<String> = listOf(),
        warningMessageList: List<String> = listOf(),
        errorMessageList: List<String> = listOf(),
    ): Any {
        redirectAttributes.addFlashAttribute("informationMessageList", informationMessageList)
        redirectAttributes.addFlashAttribute("warningMessageList", warningMessageList)
        redirectAttributes.addFlashAttribute("errorMessageList", errorMessageList)

        return "redirect:${location}"
    }

    fun view(
        model: Model,
        status: HttpStatus,
        view: View? = null,
        layout: String? = null,
        template: String? = null,
        informationMessageList: List<String> = listOf(),
        warningMessageList: List<String> = listOf(),
        errorMessageList: List<String> = listOf(),
    ): Any {
        val modelAndView = ModelAndView()
        modelAndView.status = status

        if (view != null) {
            modelAndView.view = view
        } else {
            modelAndView.addObject("layout", layout)
            modelAndView.addObject("template", template)
            modelAndView.viewName = layout ?: template ?: throw IllegalArgumentException()
        }

        modelAndView.addObject("informationMessageList", informationMessageList)
        modelAndView.addObject("warningMessageList", warningMessageList)
        modelAndView.addObject("errorMessageList", errorMessageList)

        modelAndView.addAllObjects(model.asMap())

        return modelAndView
    }

    class BadRequestException : PresentationException()

    class Try<T>(
        private val block: () -> T,
    ) {

        fun catch(vararg type: KClass<out Throwable>, catchBlock: (e: Throwable) -> T): CatchBlock<T> {
            return CatchBlock(block, listOf(type.toList() to catchBlock))
        }

        class CatchBlock<T>(
            private val block: () -> T,
            private val catchBlockList: List<Pair<Collection<KClass<out Throwable>>, (e: Throwable) -> T>>,
        ) {

            fun catch(vararg type: KClass<out Throwable>, catchBlock: (e: Throwable) -> T): CatchBlock<T> {
                return CatchBlock(block, catchBlockList + listOf(type.toList() to catchBlock))
            }

            fun rcurl(): T {
                return try {
                    block()
                } catch (e: Throwable) {
                    catchBlockList.find { it.first.contains(e::class) }?.second?.let { it(e) } ?: throw e
                }
            }

        }

    }

}
