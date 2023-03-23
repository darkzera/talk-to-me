package com.darkzera.fetcher.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class ResourceTokenConfiguration {

//    private final OAuth2UserService oAuth2UserService;
//
//    public ResourceTokenConfiguration(OAuth2UserService oAuth2UserService) {
//        this.oAuth2UserService = oAuth2UserService;
//    }

    @SneakyThrows
    @Bean
    public DefaultSecurityFilterChain httpSecurity(final HttpSecurity http){


        return http.authorizeRequests()
                .antMatchers("/**").fullyAuthenticated()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
//                .oauth2Login().userInfoEndpoint().userService(oAuth2UserService).and().and()
                .oauth2ResourceServer().jwt()
                .and().and()
                .cors().and().csrf().disable()
                .build();
    }
}