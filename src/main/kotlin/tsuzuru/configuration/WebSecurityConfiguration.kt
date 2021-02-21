package tsuzuru.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import tsuzuru.security.service.SecurityService

@Configuration
@EnableWebSecurity
class WebSecurityConfiguration(
    private val securityService: SecurityService,
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        requireNotNull(http)

        http.authorizeRequests()
            .antMatchers("/").permitAll()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/analytics/**").hasRole("USER")
            .antMatchers("/api/**").hasRole("USER")
            .antMatchers("/setting/**").hasRole("USER")
            .antMatchers("/sign-in").permitAll()
            .antMatchers("/sign-up").permitAll()
            .anyRequest().denyAll()
        http.csrf()
            .ignoringAntMatchers("/admin/proxy/**")
        http.headers()
            .frameOptions().sameOrigin()
        http.formLogin()
            .loginPage("/sign-in")
            .loginProcessingUrl("/sign-in")
            .failureUrl("/sign-in")
            .defaultSuccessUrl("/")
            .usernameParameter("name")
            .passwordParameter("password")
        http.logout()
            .logoutUrl("/sign-out")
            .logoutSuccessUrl("/")
        http.userDetailsService(securityService)
    }

}
