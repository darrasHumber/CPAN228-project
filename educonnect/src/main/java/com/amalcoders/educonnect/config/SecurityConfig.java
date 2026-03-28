package com.amalcoders.educonnect.config;

import com.amalcoders.educonnect.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Collection;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserService userService;

    public SecurityConfig(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // ── Role-based redirect after login ───────
    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    Authentication authentication) throws IOException {

                Collection<? extends GrantedAuthority> authorities =
                        authentication.getAuthorities();

                String redirect = "/courses"; // default

                for (GrantedAuthority authority : authorities) {
                    switch (authority.getAuthority()) {
                        case "ROLE_ADMIN"       -> redirect = "/admin";
                        case "ROLE_INSTRUCTOR"  -> redirect = "/instructor";
                        case "ROLE_STUDENT"     -> redirect = "/dashboard";
                    }
                }
                response.sendRedirect(redirect);
            }
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth

                        // Public pages
                        .requestMatchers(
                                "/", "/about", "/how-it-works",
                                "/login", "/register",
                                "/css/**", "/js/**", "/images/**",
                                "/h2-console/**"
                        ).permitAll()

                        // Admin only
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN")

                        // Instructor only
                        .requestMatchers("/instructor/**")
                        .hasAnyRole("ADMIN", "INSTRUCTOR")

                        // Student only
                        .requestMatchers("/dashboard/**")
                        .hasAnyRole("ADMIN", "STUDENT")

                        // Admin + Instructor can add/edit courses
                        .requestMatchers("/courses/new", "/courses/edit/**")
                        .hasAnyRole("ADMIN", "INSTRUCTOR")

                        // Any logged-in user
                        .requestMatchers("/courses", "/courses/**")
                        .authenticated()

                        // Everything else needs login
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(successHandler()) // role-based redirect
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                )
                .headers(headers -> headers
                        .frameOptions(frame -> frame.disable())
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**")
                );

        return http.build();
    }
}
