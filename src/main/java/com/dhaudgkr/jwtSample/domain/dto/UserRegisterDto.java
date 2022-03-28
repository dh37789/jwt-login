package com.dhaudgkr.jwtSample.domain.dto;

import com.dhaudgkr.jwtSample.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRegisterDto {

    private long id;
    private String username;

    @Builder
    public UserRegisterDto(long id, String username, String password) {
        this.id = id;
        this.username = username;
    }

    public UserRegisterDto toEntity(User user){
        return UserRegisterDto.builder()
                .username(user.getUsername())
                .build();
    }
}
