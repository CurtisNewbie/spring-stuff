package com.curtisnewbie.tacocloud;

import javax.sql.DataSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        // using LDAP, we can setup filters and base (where searching starts). LDAP is a centralised
        // server which stores user information and works as an authentication server.
        // !This is not working because the .LDIF file is somehow illegal.
        auth.ldapAuthentication().userSearchBase("ou=people").userSearchFilter("(uid={0})")
                .groupSearchBase("ou=groups").groupSearchFilter("member={0}").contextSource()
                .root("dc=tacocloud,dc=com").ldif("classpath:users.ldif").and().passwordCompare()
                .passwordEncoder(new BCryptPasswordEncoder()).passwordAttribute("passcode");
    }
}

