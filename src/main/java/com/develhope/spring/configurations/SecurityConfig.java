package com.develhope.spring.configurations;

import com.develhope.spring.authentication.AuthFilterJWT;
import com.develhope.spring.authentication.UzerDetailsService;
import com.develhope.spring.services.implementations.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UzerDetailsService uzerDetailsService;
    private final AuthFilterJWT authFilterJWT;

    @Autowired
    public SecurityConfig(UzerDetailsService uzerDetailsService, AuthFilterJWT authFilterJWT) {
        this.uzerDetailsService = uzerDetailsService;
        this.authFilterJWT = authFilterJWT;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(registry -> {
                    registry.requestMatchers("/signup/**", "/login/**").permitAll();
                    registry.requestMatchers("/api/v1/users/**").hasAnyRole("ARTIST", "LISTENER", "ADVERTISER");
                    registry.requestMatchers("/api/v1/subscriptions/**").hasRole("LISTENER");
                    registry.requestMatchers("/api/v1/song/**").hasAnyRole("ARTIST", "LISTENER");
                    registry.requestMatchers(HttpMethod.POST, "/api/v1/song/**", "/api/v1/song/fileService/**").hasRole("ARTIST");
                    registry.requestMatchers(HttpMethod.PUT, "/api/v1/song/**").hasRole("ARTIST");
                    registry.requestMatchers(HttpMethod.DELETE, "/api/v1/song/**", "/api/v1/song/fileService/**").hasRole("ARTIST");
                    registry.requestMatchers("/api/v1/playlists/**").hasRole("LISTENER");
                    registry.requestMatchers("/api/v1/likes/**").hasAnyRole("LISTENER", "ARTIST");
                    registry.requestMatchers("/api/v1/genre/**").hasRole("ARTIST");
                    registry.requestMatchers("/api/v1/comments/**").hasAnyRole("LISTENER", "ARTIST");
                    registry.requestMatchers("/api/v1/stream/**").hasAnyRole("LISTENER", "ARTIST");
                    registry.requestMatchers("/api/v1/albums/**").hasRole("ARTIST");
                    registry.requestMatchers("/api/v1/advertisements/**").hasRole("ADVERTISER");
                    registry.anyRequest().authenticated();
                })
                .formLogin(AbstractAuthenticationFilterConfigurer::permitAll)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .addFilterBefore(authFilterJWT, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public UzerDetailsService uzerService() {
        return uzerDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(uzerDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

}
