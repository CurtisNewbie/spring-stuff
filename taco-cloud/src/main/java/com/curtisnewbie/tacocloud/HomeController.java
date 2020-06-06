package com.curtisnewbie.tacocloud;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // for any "/" requests, redirect to home.html template
        return "home";
    }
}
