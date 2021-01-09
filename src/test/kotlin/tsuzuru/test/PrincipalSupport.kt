package tsuzuru.test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestComponent
import tsuzuru.domain.entity.UserEntity
import tsuzuru.security.principal.Principal

@TestComponent
class PrincipalSupport {

    @Autowired
    private lateinit var entitySupport: EntitySupport

    fun principal(
        userEntity: UserEntity = entitySupport.userEntity(),
    ): Principal {
        return Principal(
            userEntity = userEntity,
        )
    }

}
