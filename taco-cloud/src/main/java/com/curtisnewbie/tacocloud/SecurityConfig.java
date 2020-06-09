package com.curtisnewbie.tacocloud;

import javax.sql.DataSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

// @EnableWebSecurity enables security that is not HTTP-based. Without this SecurityConfig class,
// all webservices will still be protected by BASIC since starter-security is in pom.xml and
// classpath
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String ROLE_USER = "ROLE_USER";

    private final DataSource dataSource;

    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // overwrite default security configuration, e.g., how user credentials are stored
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // by default, spring will try to generate query to retrieve username and passowrd etc.
        // but we can do it on our own
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery("SELECT name, password, enabled from User WHERE name = ?")
                .authoritiesByUsernameQuery("SELECT name, auth FROM Authority WHERE name = ?")
                .passwordEncoder(NoOpPasswordEncoder.getInstance()); // plaintext encoder
        // .passwordEncoder(new StandardPasswordEncoder("b123kdc")); // e.g., this one use SHA256
    }
}

