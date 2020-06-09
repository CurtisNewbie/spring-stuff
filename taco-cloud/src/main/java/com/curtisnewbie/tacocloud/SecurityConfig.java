package com.curtisnewbie.tacocloud;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// @EnableWebSecurity enables security that is not HTTP-based. Without this SecurityConfig class,
// all webservices will still be protected by BASIC since starter-security is in pom.xml and
// classpath
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String ROLE_USER = "ROLE_USER";

    // overwrite default security configuration, e.g., how user credentials are stored
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // after spring5.0, we need to select passwordEncoder, while prior 5.0, the default is noop
        // password encoder.
        // https://mkyong.com/spring-boot/spring-security-there-is-no-passwordencoder-mapped-for-the-id-null/
        auth.inMemoryAuthentication().withUser("buzz").password("{noop}infinity")
                .authorities(ROLE_USER).and().withUser("woody").password("{noop}bullseye")
                .authorities(ROLE_USER);
    }
}

