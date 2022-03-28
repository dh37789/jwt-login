package com.dhaudgkr.jwtSample.domain.dto;

import com.dhaudgkr.jwtSample.web.dto.TokenDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserLoginDto {
    private long id;
    private String username;
    private TokenDto accessToken;

    @Builder
    public UserLoginDto(long id, String username, TokenDto accessToken) {
        this.id = id;
        this.username = username;
        this.accessToken = accessToken;
    }
}
