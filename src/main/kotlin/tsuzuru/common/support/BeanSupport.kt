package tsuzuru.common.support

import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct
import kotlin.reflect.KClass

@Component
class BeanSupport(
    private val applicationContext: ApplicationContext,
) {

    @PostConstruct
    private fun initialize() {
        beanSupport = this
    }

    companion object {

        private lateinit var beanSupport: BeanSupport

        fun <T : Any> getBean(klass: KClass<T>): T {
            return beanSupport.applicationContext.getBean(klass.java)
        }

    }

}
