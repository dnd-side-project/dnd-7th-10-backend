package com.io.linkapp.user.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.io.linkapp.common.BaseTimeEntity;
import com.io.linkapp.link.domain.Article;
import com.io.linkapp.link.domain.Folder;
import com.io.linkapp.link.domain.Memo;
import com.io.linkapp.link.domain.Remind;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Getter
@Table(name = "users")
@Entity
@NoArgsConstructor
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(name = "user_id")
    private UUID id;

    private String username;

    private String password;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value = "user-folder")
    private List<Folder> folders = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value = "user-memo")
    private List<Memo> memos = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference(value = "user-article")
    private List<Article> articles = new ArrayList<>();
    
    
    //다대일[N:1] 양방향 매핑 처럼 외래키(==참조키. 즉 상대방에서 어떤 컬럼을 기준으로 할지)가 있는 곳이 연관관계의 주인
    // 따라서 얘는 주인이 아님 == 조회만 가능함
    // 주 테이블에 외래키가 없는 경우에는 onetoone 단방향을 지원하지 않음
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private List<Remind> remind;
    
    
    @Enumerated(value = EnumType.STRING)
    private Role role = Role.BASIC;

    @Builder
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Builder
    public User(UUID id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
}
