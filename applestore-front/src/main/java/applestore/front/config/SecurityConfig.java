package applestore.front.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

/**
 * @author chanwook
 */
@Configuration
@EnableWebSecurity
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
        auth.
            inMemoryAuthentication()
                .withUser("chanwook").password("1234").roles("USERS", "ADMIN")
        .and();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http.
            authorizeRequests()
                .antMatchers("/cart/**").authenticated()
                .antMatchers("/order/**").authenticated()
            .and()
            .formLogin();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
}
