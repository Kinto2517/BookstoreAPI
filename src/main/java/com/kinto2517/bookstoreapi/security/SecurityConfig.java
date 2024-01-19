package com.kinto2517.bookstoreapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static com.kinto2517.bookstoreapi.enums.Permission.*;
import static com.kinto2517.bookstoreapi.enums.Role.*;
import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private static final String[] WHITE_LIST_URL = {"/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger",
            "/login",
            "/swagger-ui.html"};
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers("/login").permitAll()
                                .requestMatchers("/api/v1/clients/**").hasAnyRole(CLIENT.name(), ADMIN.name())
                                .requestMatchers(GET, "/api/v1/clients/**").hasAnyAuthority(CLIENT_READ.name(), ADMIN_READ.name())
                                .requestMatchers(POST, "/api/v1/clients/**").hasAnyAuthority(CLIENT_CREATE.name(), ADMIN_CREATE.name())
                                .requestMatchers(PUT, "/api/v1/clients/**").hasAnyAuthority(CLIENT_UPDATE.name(), ADMIN_UPDATE.name())
                                .requestMatchers(DELETE, "/api/v1/clients/**").hasAnyAuthority(CLIENT_DELETE.name(), ADMIN_DELETE.name())
                                .anyRequest()
                                .authenticated()
                )
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
                ;

        return http.build();
    }
}