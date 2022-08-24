package com.io.linkapp.link.service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.net.HttpHeaders;
import com.io.linkapp.exception.CustomGlobalException;
import com.io.linkapp.exception.ErrorCode;
import com.io.linkapp.link.domain.Article;
import com.io.linkapp.link.domain.QArticle;
import com.io.linkapp.link.domain.QRemind;
import com.io.linkapp.link.domain.Remind;
import com.io.linkapp.link.repository.ArticleRepository;
import com.io.linkapp.link.repository.RemindRepository;
import com.io.linkapp.link.response.FcmMessage;
import com.io.linkapp.user.domain.QUser;
import com.io.linkapp.user.domain.User;
import com.io.linkapp.user.repository.UserRepository;
import com.querydsl.core.BooleanBuilder;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FirebaseCloudMessageService {
    
    private final UserRepository userRepository;
    
    private final ArticleRepository articleRepository;
    
    //메시지 전송을 위해 요청하는 주소
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/linkkle-inandout/messages:send";
    private final ObjectMapper objectMapper;
    
    public void sendMessageTo(UUID userId,String targetToken,List<UUID> articleIds) throws IOException {
        // 실제로 전달하는 메시지
        String message = makeMessage(userId,targetToken,articleIds);
        
        OkHttpClient client = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(message,
            MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
            .url(API_URL)
            .post(requestBody)
            .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())// fcm 서버에 대한 접근 jwt
            .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
            .build();
        
        Response response = client.newCall(request).execute(); //요청 후 돌아오는 응답
        
        System.out.println(response.body().string());
    }
    
    private String makeMessage(UUID userId, String targetToken,List<UUID> articleIds) throws JsonParseException, JsonProcessingException {
        
        //현재 유저에 해당되는 리마인드 찾기
        User user = userRepository.findById(userId).orElseThrow(()->{
            throw new CustomGlobalException(ErrorCode.USER_NOT_FOUND);
        });

        //그리고 아티클 리스트에서 푸시할 아티클 하나 임의 추출
        //랜덤으로 추출된 아티클의 인덱스
        int idx = (int)((Math.random()*10000)%(articleIds.size()-1));
        UUID articleId = articleIds.get(idx);
        
        Article article = articleRepository.findById(articleId)
            .orElseThrow(()-> new CustomGlobalException(ErrorCode.ARTICLE_NOT_FOUND));
        
        UUID remindId = article.getRemindId();
        
        FcmMessage fcmMessage = FcmMessage.builder()
            .message(FcmMessage.Message.builder()
                .token(targetToken)
                .notification(FcmMessage.Notification.builder()
                    .title("push title")
                    .body("push body")
                    .build()
                )
                .data(FcmMessage.Data.builder()
                    .articleId(articleId)
                    .remindId(remindId)
                    .build())
                .build()).validate_only(false).build();
    
        System.out.println(objectMapper.writeValueAsString(fcmMessage));
        
        return objectMapper.writeValueAsString(fcmMessage);
    }
    
    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase/firebase_service_key.json";
        
        GoogleCredentials googleCredentials = GoogleCredentials
            .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
            .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
        
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }

}
