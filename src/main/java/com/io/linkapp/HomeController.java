package com.io.linkapp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Hello! This is Linggle App Index page. Please visit 'www.linggle-io.ml/api/members'.";
    }

    @GetMapping("/api")
    public String api() {
        return "api";
    }
}
