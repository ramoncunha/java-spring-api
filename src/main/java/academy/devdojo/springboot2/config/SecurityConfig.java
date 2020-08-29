package academy.devdojo.springboot2.config;

import academy.devdojo.springboot2.service.DevDojoUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DevDojoUserService devDojoUserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
            .disable()
//            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//            .and()
            .authorizeRequests()
                .antMatchers("/animes/admin/**").hasRole("ADMIN")
                .antMatchers("/animes/").hasRole("USER")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .and()
            .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        log.info("Testing encoder password {}", passwordEncoder.encode("academy"));
//        auth.inMemoryAuthentication()
//            .withUser("devdojo")
//            .password(passwordEncoder.encode("academy"))
//            .roles("USER")
//            .and()
//            .withUser("ramon")
//            .password(passwordEncoder.encode("academy"))
//            .roles("USER", "ADMIN");

        auth.userDetailsService(devDojoUserService)
            .passwordEncoder(passwordEncoder);

    }

}
