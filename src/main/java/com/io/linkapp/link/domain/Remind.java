package com.io.linkapp.link.domain;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Remind {
    
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "remind_id",nullable=false)
    private UUID remindId;
    
    
    @Column(name = "remind_title",nullable=false)
    private String remindTitle;
    
    @Column(name = "user_id")
    private UUID userId;
    
    
//    @OneToOne
//    @JoinColumn(name="user_id")
//    private User user;
    
}
