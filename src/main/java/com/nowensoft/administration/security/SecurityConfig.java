package com.nowensoft.administration.security;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nowensoft.administration.services.admin.RoleRutaService;
import com.nowensoft.administration.services.admin.security.UsuarioDetailsServiceImpl;

@Configuration
public class SecurityConfig {
    @Autowired
    @Lazy
    private UsuarioDetailsServiceImpl usuarioDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRutaService roleRutaService;

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    public SecurityConfig() {
        logger.info("SecurityConfig initialized!");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioDetailsService).passwordEncoder(passwordEncoder);
    }

    // Configuración de seguridad HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        List<Map<String, String>> routesWithRoles = roleRutaService.getRutasWithRoles();
        http
         .sessionManagement(sessionManagement -> sessionManagement
            .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
        )
                .authorizeHttpRequests(authorizeRequests -> {

                    authorizeRequests

                            // Rutas accesibles solo para ADMIN
                            // .requestMatchers("/admin/**", "/permisos/**", "/roles/**").hasRole("ADMIN")
                            // .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN") // Rutas accesibles
                            // para USER y ADMIN
                            .requestMatchers("/administration/acceso-denegado").permitAll()
                            .requestMatchers("/","/administration", "/public/**").permitAll() // Rutas accesibles para todos
                            // Archivos estáticos accesibles para todos
                            .requestMatchers("/static/**", "/dist/**", "/plugins/**").permitAll();

                    // Configurar rutas dinámicas basadas en roles
                    for (Map<String, String> route : routesWithRoles) {
                        String url = route.get("url");
                        String role = route.get("role");
                        authorizeRequests.requestMatchers(url).hasRole(role);
                    }

                    authorizeRequests.anyRequest().authenticated(); // Cualquier otra ruta requiere autenticación

                })
                // Redirigir a la página de acceso denegado
                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedPage("/administration/acceso-denegado"))

                .formLogin(formLogin -> formLogin
                        .loginPage("/administration/login") // Página de inicio de sesión personalizada
                        .defaultSuccessUrl("/administration", true)  // Redirige a "/index" después de iniciar sesión
                        .permitAll() // Permitir acceso a la página de login sin autenticación
                )
                .logout(logout -> logout
                        .permitAll() // Permitir a todos cerrar sesión
                        .logoutUrl("/administration/logout") // URL para cerrar sesión
                        .logoutSuccessUrl("/administration") // URL a la que se redirige después de cerrar sesión
                        .invalidateHttpSession(true) // Invalidar la sesión HTTP
                        .deleteCookies("JSESSIONID") // Eliminar cookies de sesión
                );

        return http.build();
    }

}
