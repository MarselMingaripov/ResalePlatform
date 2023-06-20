package ru.min.resaleplatform.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.min.resaleplatform.repository.UserRepository;
import ru.min.resaleplatform.security.service.impl.UserDetailsServiceImpl;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserRepository userRepository;

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/login",
            "/register", "/ads"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests(authorize -> authorize
                        .mvcMatchers(AUTH_WHITELIST).permitAll()
                        .mvcMatchers("/ads/**", "/users/**").authenticated()
                        .anyRequest().authenticated())
                .cors()
                .and()
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
