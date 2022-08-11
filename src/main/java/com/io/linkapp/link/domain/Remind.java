package com.io.linkapp.link.domain;

import com.io.linkapp.user.domain.User;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
public class Remind {
    
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "remind_id",nullable=false)
    private UUID remindId;
    
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
    
    
    @Column(name = "remind_title",nullable=false)
    private String remindTitle;
    
}
