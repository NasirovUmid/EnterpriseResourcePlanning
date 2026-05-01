package com.pm.EnterpriseResourcePlanning.configuration;

import com.pm.EnterpriseResourcePlanning.filter.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {

        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index.html", "/pages/**", "/css/**", "/js/**", "/static/**", "/auth/**", "/users/roles/roles").permitAll()
                        // Modules: ADMIN full access; any authenticated user can GET; MOD roles can PUT
                        .requestMatchers(HttpMethod.GET, "/modules/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/modules/**").hasAnyRole("ADMIN", "MOD_PROJECTS", "MOD_ORGANIZATIONS", "MOD_USERS", "MOD_PRODUCTS", "MOD_CONTRACTS")
                        .requestMatchers("/roles/**", "/permissions/**", "/actions/**", "/modules/**").hasRole("ADMIN")
                        // Users
                        .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("ADMIN", "MOD_USERS")
                        .requestMatchers(HttpMethod.POST, "/users/**").hasAnyRole("ADMIN", "MOD_USERS")
                        .requestMatchers(HttpMethod.PUT, "/users/**").authenticated()
                        .requestMatchers(HttpMethod.PATCH, "/users/**").hasAnyRole("ADMIN", "MOD_USERS")
                        // Projects
                        .requestMatchers(HttpMethod.GET, "/projects").hasAnyRole("ADMIN", "MOD_PROJECTS")
                        .requestMatchers(HttpMethod.POST, "/projects/**").hasAnyRole("ADMIN", "MOD_PROJECTS")
                        .requestMatchers(HttpMethod.PUT, "/projects/**").hasAnyRole("ADMIN", "MOD_PROJECTS")
                        .requestMatchers(HttpMethod.PATCH, "/projects/**").hasAnyRole("ADMIN", "MOD_PROJECTS")
                        // Organizations
                        .requestMatchers(HttpMethod.GET, "/organizations").hasAnyRole("ADMIN", "MOD_ORGANIZATIONS")
                        .requestMatchers(HttpMethod.POST, "/organizations/**").hasAnyRole("ADMIN", "MOD_ORGANIZATIONS")
                        .requestMatchers(HttpMethod.PUT, "/organizations/**").hasAnyRole("ADMIN", "MOD_ORGANIZATIONS")
                        .requestMatchers(HttpMethod.PATCH, "/organizations/**").hasAnyRole("ADMIN", "MOD_ORGANIZATIONS")
                        // Products
                        .requestMatchers(HttpMethod.GET, "/products").hasAnyRole("ADMIN", "MOD_PRODUCTS")
                        .requestMatchers(HttpMethod.POST, "/products/**").hasAnyRole("ADMIN", "MOD_PRODUCTS")
                        .requestMatchers(HttpMethod.PUT, "/products/**").hasAnyRole("ADMIN", "MOD_PRODUCTS")
                        .requestMatchers(HttpMethod.PATCH, "/products/**").hasAnyRole("ADMIN", "MOD_PRODUCTS")
                        // Contracts
                        .requestMatchers(HttpMethod.GET, "/contracts").hasAnyRole("ADMIN", "MOD_CONTRACTS")
                        .requestMatchers(HttpMethod.POST, "/contracts/**").hasAnyRole("ADMIN", "MOD_CONTRACTS")
                        .requestMatchers(HttpMethod.DELETE, "/contracts/**").hasAnyRole("ADMIN", "MOD_CONTRACTS")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
