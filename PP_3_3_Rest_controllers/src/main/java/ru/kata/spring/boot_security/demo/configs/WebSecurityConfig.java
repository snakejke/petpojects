package ru.kata.spring.boot_security.demo.configs;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.kata.spring.boot_security.demo.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final SuccessUserHandler successUserHandler;
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    public void setUserService(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests()
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .requestMatchers("/user").hasAnyAuthority("USER", "ADMIN")
                .requestMatchers("/").permitAll()
                .requestMatchers("/**/*.js", "/**/*.css").permitAll()
                .requestMatchers("/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs/**", "/v3/api-docs/**").permitAll()
                .requestMatchers("/api/auth/login", "/api/auth/logout").permitAll()
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .successHandler(successUserHandler)
                .permitAll()
            .and()
                .formLogin()
                    .loginPage("/api/auth/login")
                    .usernameParameter("email")
                    .permitAll()
            .and()
            .httpBasic()
            .and()
            .exceptionHandling()
                    .accessDeniedPage("/403")
                    .authenticationEntryPoint((request, response, authException) -> {
                        response.setContentType("application/json;charset=UTF-8");
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"Access is denied due to invalid credentials.\"}");
                })
            .and()
            .logout()
                .logoutSuccessUrl("/")
                .permitAll();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        return authenticationProvider;
    }
}
