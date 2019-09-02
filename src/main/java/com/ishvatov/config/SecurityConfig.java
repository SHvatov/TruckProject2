package com.ishvatov.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

/**
 * Security configuration class.
 *
 * @author Sergey Khvatov
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * {@link UserDetailsService} instance.
     */
    private UserDetailsService userDetailsService;

    /**
     * Default class constructor, used for the field injection.
     *
     * @param userDetailsService {@link UserDetailsService} instance.
     */
    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Configure {@link DaoAuthenticationProvider} object.
     *
     * @return configured instance of the {@link DaoAuthenticationProvider}.
     */
    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    /**
     * Creates an instance of the {@link BCryptPasswordEncoder}, which is used
     * to encrypt the passwords.
     *
     * @return Singleton instance of the {@link BCryptPasswordEncoder}.
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Implementation of the configure method.
     *
     * @param auth {@link AuthenticationManagerBuilder} instance.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    /**
     * Implementation of the {@link HttpSecurity} object configure method.
     *
     * @param http {@link HttpSecurity} instance to configure.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic()
            .and()
            .authorizeRequests()
            .antMatchers("/login/authenticate").permitAll()
            .antMatchers("/employee/**").hasRole("USER")
            .antMatchers("/driver/**").hasRole("DRIVER")
            .anyRequest().authenticated()
            .and()
            .sessionManagement().disable()
            .csrf().disable()
            .formLogin().disable();
    }
}
