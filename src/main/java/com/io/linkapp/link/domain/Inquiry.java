package com.io.linkapp.link.domain;

import com.io.linkapp.common.BaseTimeEntity;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Data
public class Inquiry extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    private UUID id;
    
    @Column(name = "user_id")
    private UUID userId;
    
    private String inquiryTitle;
    private String inquiry;
    
    private String answerTitle;
    private String answer;
    
    private boolean isAnswered;
    
    public boolean getIsAnswered(){
        return this.isAnswered;
    }
    
}
