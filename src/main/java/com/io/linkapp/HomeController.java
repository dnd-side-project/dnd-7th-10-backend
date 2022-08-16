package com.io.linkapp;

import com.io.linkapp.config.security.auth.PrincipalDetails;
import com.io.linkapp.user.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final RedisService redisService;

    @ResponseBody
    @GetMapping("/")
    public String home(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        return "Hello! This is Linkkle App Index page. Please visit 'www.linkkle-io.ml/api/members'.";
    }

    @GetMapping("/kakao")
    public String oauth2TestApi() {
        return "login.html";
    }

    @GetMapping("/swagger")
    public String swagger() {
        return "redirect:/swagger-ui.html";
    }

    @ResponseBody
    @GetMapping("/redis")
    public String redis(@AuthenticationPrincipal PrincipalDetails principalDetails){
        String username = principalDetails.getUser().getUsername();
        String refreshToken = redisService.getValues(username);
        return refreshToken;
    }
}
