package com.io.linkapp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {

    @ResponseBody
    @GetMapping("/")
    public String home() {
        return "Hello! This is Linggle App Index page. Please visit 'www.linggle-io.ml/api/members'.";
    }

    @GetMapping("/google")
    public String api() {
        System.out.println("====");
        return "login.html";
    }
}
