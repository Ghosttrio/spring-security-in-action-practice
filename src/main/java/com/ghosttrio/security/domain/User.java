package com.ghosttrio.security.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class User {
    @Id
    private Long id;
    private String username;
    private String password;
    private String authority;
}