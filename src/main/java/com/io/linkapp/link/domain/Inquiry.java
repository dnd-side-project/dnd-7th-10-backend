package com.io.linkapp.link.domain;

import com.io.linkapp.common.BaseTimeEntity;
import com.io.linkapp.user.domain.User;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
    
    @OneToOne
    @JoinColumn(name="user_id",referencedColumnName = "user_id",insertable = false, updatable = false)
    private User user;
    
    private String inquiryTitle;
    private String inquiry;
    
    private String answerTitle;
    private String answer;
    
    private boolean isAnswered;
    
    public boolean getIsAnswered(){
        return this.isAnswered;
    }
    
}
