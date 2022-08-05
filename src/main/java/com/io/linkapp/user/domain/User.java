package com.io.linkapp.user.domain;

import lombok.Getter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Table(name = "users")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String roles;

    public User passwordEncode(BCryptPasswordEncoder bCryptPasswordEncoder){
        this.password = bCryptPasswordEncoder.encode(password);
        return this;
    }

    public List<String> getRoleList(){
        if(this.roles.length() > 0){
            return Arrays.asList(this.roles.split(","));
        }

        return new ArrayList<>();
    }
}
