package com.io.linkapp.link.controller.api;


import com.io.linkapp.config.security.auth.PrincipalDetails;
import com.io.linkapp.link.request.PushRequest;
import com.io.linkapp.link.service.FirebaseCloudMessageService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PushApi {
    
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    
//    @PostMapping("/api/fcm")
//    public ResponseEntity pushMessage(@RequestBody PushRequest pushRequest,@AuthenticationPrincipal PrincipalDetails principalDetails) throws IOException {
//
////        System.out.println(pushRequest.getTargetToken());
//
//        firebaseCloudMessageService.sendMessageTo(principalDetails.getUser(),
//            pushRequest.getTargetToken());
//        return ResponseEntity.ok().build();
//    }
    
    
}
