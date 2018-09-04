package com.haiercash.pluslink.capital.processer.server.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/**", "/api/processer/loan/**", "/static/**").permitAll()
                .antMatchers("/druid/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .headers().defaultsDisabled().cacheControl();
    }
}
