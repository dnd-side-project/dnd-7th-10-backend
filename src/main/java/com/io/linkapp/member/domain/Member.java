package com.io.linkapp.member.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Member {

    @Id @GeneratedValue
    private Long id;

    private String password;
}
