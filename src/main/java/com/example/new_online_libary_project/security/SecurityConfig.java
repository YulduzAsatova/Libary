package com.example.new_online_libary_project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true

)
public class SecurityConfig {
    private static final String[] WHITE_LIST = {"/css/**",
            "/images/**",
            "/fonts/**",
            "/scripts/**",
            "/auth/login",
            "/auth/register","/"};
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity ) throws Exception
    {
        return httpSecurity
                //.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        registry -> registry
                                .requestMatchers( WHITE_LIST )
                                .permitAll()
                                .anyRequest()
                                .fullyAuthenticated()//.authenticated()
                )
                .formLogin(
                        loginConfig -> loginConfig
                                .loginPage( "/auth/login" )
                                .loginProcessingUrl( "/auth/login" )
                                .usernameParameter( "phoneNumber" )
                                .passwordParameter( "password" )
                                .defaultSuccessUrl( "/", true )
                )
                .logout(
                        logoutConfig -> logoutConfig
                                .logoutSuccessUrl( "/auth/login" )
                                .logoutRequestMatcher( new AntPathRequestMatcher( "/auth/logout" ) )
                )
                .rememberMe(
                        rememberMeConfig -> rememberMeConfig
                                .rememberMeCookieName( "rememberMe" )
                                .rememberMeParameter( "rememberMe" )
                                .tokenValiditySeconds(24 * 60 * 60)
                                .key("secret_key")
                                //.userDetailsService(userDetailsService)
                )
                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
