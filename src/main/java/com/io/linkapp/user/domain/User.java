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

import javax.persistence.*;
import java.util.ArrayList;
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

    private String nickname;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonManagedReference(value = "user-folder")
    private List<Folder> folders = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonManagedReference(value = "user-memo")
    private List<Memo> memos = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    @JsonManagedReference(value = "user-article")
    private List<Article> articles = new ArrayList<>();
    
    @OneToMany
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private List<Remind> remindList;

    @Enumerated(value = EnumType.STRING)
    private Role role = Role.BASIC;

    @Builder
    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    @Builder
    public User(UUID id, String username, Role role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
    
    
    
}
