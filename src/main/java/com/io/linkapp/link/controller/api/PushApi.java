package com.io.linkapp.link.controller.api;


import com.io.linkapp.link.request.PushRequest;
import com.io.linkapp.link.service.FirebaseCloudMessageService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PushApi {
    
    private final FirebaseCloudMessageService firebaseCloudMessageService;
    
    @PostMapping("/api/fcm")
    public ResponseEntity pushMessage(@RequestBody PushRequest pushRequest) throws IOException {
        System.out.println(pushRequest.getTargetToken() + " "
            +pushRequest.getTitle() + " " + pushRequest.getBody());
        
        firebaseCloudMessageService.sendMessageTo(
            pushRequest.getTargetToken(),
            pushRequest.getTitle(),
            pushRequest.getBody());
        return ResponseEntity.ok().build();
    }
    
    
}
