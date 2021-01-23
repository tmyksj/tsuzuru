package tsuzuru.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional
import tsuzuru.domain.entity.UserEntity
import tsuzuru.domain.repository.UserRepository
import javax.annotation.PostConstruct

@Configuration
@Transactional
class AdminConfiguration(
    private val userRepository: UserRepository,
) {

    @PostConstruct
    fun initialize() {
        if (userRepository.existsByRole(UserEntity.Role.Admin)) {
            return
        }

        userRepository.save(
            UserEntity.build(
                name = "admin",
                passwordRaw = "password",
                roleList = UserEntity.Role.values().toList(),
            )
        )
    }

}
