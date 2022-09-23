package com.io.linkapp.link.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
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

    @Column(name="cron")
    private String cron;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "remind_id",referencedColumnName="remind_id")
    private List<Article> articleList = new ArrayList<>();
}
