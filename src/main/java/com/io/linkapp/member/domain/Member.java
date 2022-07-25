package com.io.linkapp.member.domain;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String password;
}
