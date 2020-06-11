package com.curtisnewbie.tacocloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

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

    @Bean
    @Profile({"prod", "default"}) // example of using profiles
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder("54cr3t");
    }

    // overwrite default security configuration, e.g., how user credentials are
    // stored
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    // rules, custom login page, etc.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // rules declared first have higher priority than the following
        http.authorizeRequests().antMatchers("/design", "/orders").hasRole("USER")
                // custom login page, when user is not authenticated, redirect to /login instead
                // of returning 403 reponses. If the user is authenticated, redirect to /design
                .antMatchers("/", "/**").permitAll().and().formLogin().loginPage("/login")
                .defaultSuccessUrl("/design")
                // custom logout page and where should user redirect to when logged out. The default
                // logout page will be in "/logout"
                .and().logout().logoutSuccessUrl("/login");
    }
}
