package com.io.linkapp.user.domain;

import com.io.linkapp.common.BaseTimeEntity;
import com.io.linkapp.link.domain.Article;
import com.io.linkapp.link.domain.Folder;
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
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Folder> folders = new ArrayList<>();

    @Enumerated(value = EnumType.STRING)
    private Role role = Role.BASIC;

    @Builder
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
