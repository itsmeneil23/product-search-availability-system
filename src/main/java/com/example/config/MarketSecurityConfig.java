package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.service.MarketUserDetailsService;

@Configuration
@EnableWebSecurity
public class MarketSecurityConfig {

    @Autowired
    private MarketUserDetailsService marketUserDetailsService;

    @Bean
    @Order(3)
    public SecurityFilterChain marketSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/market/**", "/market-login", "/market-logout")
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers("/market-login").permitAll()
                .requestMatchers("/market/**").authenticated()
            )
            .formLogin((form) -> form
                .loginPage("/market-login")
                .loginProcessingUrl("/market-login")
                .usernameParameter("name")
                .passwordParameter("password")
                .defaultSuccessUrl("/market")
                .failureUrl("/market-login?error=true")
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutUrl("/market-logout")
                .logoutSuccessUrl("/market-login?logout=true")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .permitAll()
            )
            .sessionManagement((session) -> session
                .sessionCreationPolicy(org.springframework.security.config.http.SessionCreationPolicy.IF_REQUIRED)
            )
            .authenticationProvider(marketAuthenticationProvider())
            .csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public AuthenticationProvider marketAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(marketUserDetailsService);
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return provider;
    }
} 