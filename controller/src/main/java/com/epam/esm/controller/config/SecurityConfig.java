package com.epam.esm.controller.config;

import com.epam.esm.controller.util.JwtFilter;
import com.epam.esm.repository.model.entity.Role;
import com.epam.esm.service.impl.UserServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableConfigurationProperties
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserServiceRepo userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    public SecurityConfig(UserServiceRepo userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()

                .antMatchers(HttpMethod.GET, "/v1/tags/**").hasAnyRole(
                Role.RoleNames.USER, Role.RoleNames.ADMIN)
                .antMatchers(HttpMethod.GET, "/v1/users/**").hasAnyRole(
                Role.RoleNames.USER, Role.RoleNames.ADMIN)
                .antMatchers(HttpMethod.GET, "/v1/orders/**").hasAnyRole(
                Role.RoleNames.USER, Role.RoleNames.ADMIN)
                .antMatchers(HttpMethod.POST, "/v1/orders/**").hasAnyRole(
                Role.RoleNames.USER, Role.RoleNames.ADMIN)

                .antMatchers(HttpMethod.GET, "/v1/gift-certificates/**").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/users").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/users/auth").permitAll()

                .antMatchers("/v1/orders/**").hasRole(Role.RoleNames.ADMIN)
                .antMatchers("/v1/users/**").hasRole(Role.RoleNames.ADMIN)
                .antMatchers("/v1/gift-certificates/**").hasRole(Role.RoleNames.ADMIN)
                .antMatchers("/v1/tags/**").hasRole(Role.RoleNames.ADMIN)

                .antMatchers("/v1/**").denyAll()

                .and()
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

}
