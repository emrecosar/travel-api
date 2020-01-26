package com.api.travel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Application Security Configuration;
 * - Basic oAuth for API queries
 * - Basic oAuth for metrics
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${app.user}")
    String appUser;

    @Value("${app.password}")
    String appPassword;

    @Value("${actuator.user}")
    String actuatorUser;

    @Value("${actuator.password}")
    String actuatorPassword;

    @Autowired
    PasswordEncoder passwordEncoder;

    /**
     * Add oAuth definition and roles for specific endpoints
     *
     * @param httpSecurity http security object
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/h2-console/**")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers("/locations")
                .hasAnyRole("USER")
                .and()
                .authorizeRequests()
                .antMatchers("/actuator")
                .hasAnyRole("ADMIN")
                .and()
                .httpBasic();
        //httpSecurity.headers().frameOptions().disable();
    }

    /**
     * User definitions with credentials and roles
     *
     * @param auth oAuth manager builder
     * @throws Exception
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.inMemoryAuthentication()
                .withUser(appUser)
                .password(passwordEncoder.encode(appPassword))
                .roles("USER")
                .and()
                .withUser(actuatorUser)
                .password(passwordEncoder.encode(actuatorPassword))
                .roles("ADMIN");
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}


