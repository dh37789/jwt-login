package com.dhaudgkr.jwtSample.web.dto;

import com.dhaudgkr.jwtSample.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserRegisterRequestDto {
    private String username;
    private String password;

    @Builder
    public UserRegisterRequestDto(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .build();
    }
}
