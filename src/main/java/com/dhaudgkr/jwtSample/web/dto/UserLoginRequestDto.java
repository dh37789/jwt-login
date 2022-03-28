package com.dhaudgkr.jwtSample.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserLoginRequestDto {
    private String username;
    private String password;

    @Builder
    public UserLoginRequestDto(String username, String password){
        this.username = username;
        this.password = password;
    }

}
