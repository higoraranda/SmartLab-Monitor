package com.smartlab.monitor.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth
                // Rotas de escrita de políticas — apenas gestor autenticado
                .requestMatchers(HttpMethod.POST,   "/politicas/**", "/politicas-horario/**").authenticated()
                .requestMatchers(HttpMethod.PUT,    "/politicas/**", "/politicas-horario/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/politicas/**", "/politicas-horario/**").authenticated()
                // Todo o restante é público
                .anyRequest().permitAll()
            )

            .formLogin(form -> form
                .loginProcessingUrl("/auth/login")
                .successHandler((req, res, auth) -> {
                    res.setStatus(HttpServletResponse.SC_OK);
                    res.setContentType("application/json;charset=UTF-8");
                    res.getWriter().write("{\"autenticado\":true,\"usuario\":\"" + auth.getName() + "\"}");
                })
                .failureHandler((req, res, ex) -> {
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    res.setContentType("application/json;charset=UTF-8");
                    res.getWriter().write("{\"erro\":\"Usuário ou senha incorretos.\"}");
                })
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessHandler((req, res, auth) -> {
                    res.setStatus(HttpServletResponse.SC_OK);
                    res.setContentType("application/json;charset=UTF-8");
                    res.getWriter().write("{\"autenticado\":false}");
                })
                .permitAll()
            )

            .exceptionHandling(ex -> ex
                .authenticationEntryPoint((req, res, authEx) -> {
                    res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    res.setContentType("application/json;charset=UTF-8");
                    res.getWriter().write("{\"erro\":\"Acesso restrito ao gestor.\"}");
                })
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        var gestor = User.builder()
                .username("gestor")
                .password(encoder.encode("senha"))
                .roles("GESTOR")
                .build();
        return new InMemoryUserDetailsManager(gestor);
    }
}
