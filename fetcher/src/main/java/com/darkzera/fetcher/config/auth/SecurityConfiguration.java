package com.darkzera.fetcher.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    JwtDecoder jwtDecoder;

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http)throws Exception {

        return http.authorizeRequests()
                .antMatchers("/**").fullyAuthenticated()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt().and().and()
                .authenticationProvider(new CustomAuthenticationSuccessHandler(jwtDecoder))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .cors().and().csrf().disable()
                .build();
    }




}

