package com.io.linkapp;

import com.io.linkapp.config.security.auth.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
public class HomeController {

    @ResponseBody
    @GetMapping("/")
    public String home(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        log.info("username = {}", principalDetails.getUsername());
        return "Hello! This is Linkkle App Index page. Please visit 'www.linggle-io.ml/api/members'.";
    }

    @GetMapping("/kakao")
    public String oauth2TestApi() {
        return "login.html";
    }
}
