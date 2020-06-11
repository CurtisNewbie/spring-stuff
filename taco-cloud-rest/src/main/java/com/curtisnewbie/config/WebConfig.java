package com.curtisnewbie.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // this is essentially a different way to have a controller for the assocated
        // view or requests, e.g., we can have a controller created for the requests of
        // "/login", but since it's a very basic/simple controller, we can register it
        // here instead of creating the class for nothing.
        registry.addViewController("/").setViewName("home");
        registry.addViewController("/login");
    }

}
