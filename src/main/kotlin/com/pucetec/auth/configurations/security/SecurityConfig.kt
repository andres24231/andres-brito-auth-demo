package com.pucetec.auth.configurations.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(
                        "/api/health"
                    ).permitAll()
                    .requestMatchers("/api/admin/**").hasRole("ADMINS")
                    .requestMatchers("/api/secure-data").hasAnyRole("ADMINS", "SUPERUSERS")
                    .requestMatchers("/api/access").hasAnyRole("SUPERUSERS", "USERS")
                    .requestMatchers("/api/controls").hasAnyRole("ADMINS", "USERS")
                    .anyRequest().authenticated()

            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt { Customizer.withDefaults<JwtDecoder>() }
            }

        return http.build()
    }

}