package com.curtisnewbie.tacocloud;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// @EnableWebSecurity enables security that is not HTTP-based. Without this SecurityConfig class,
// all webservices will still be protected by BASIC since starter-security is in pom.xml and
// classpath
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String ROLE_USER = "ROLE_USER";
    private UserRepositoryUserDetailsService userDetailsService;

    public SecurityConfig(UserRepositoryUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    // overwrite default security configuration, e.g., how user credentials are
    // stored
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
