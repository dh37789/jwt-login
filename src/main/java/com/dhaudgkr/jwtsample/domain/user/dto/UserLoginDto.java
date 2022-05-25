package com.dhaudgkr.jwtsample.domain.user.dto;

import com.dhaudgkr.jwtsample.domain.token.dto.TokenDto;
import com.dhaudgkr.jwtsample.global.config.common.LoginEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class UserLoginDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Request {
        private String username;
        private String password;

        @Builder
        public Request (String username, String password) {
            this.username = username;
            this.password = password;
        }

        public void encodePassword(String encodePassword) {
            this.password = encodePassword;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {

        private int code;
        private String message;
        private TokenDto tokenDto;

        @Builder
        public Response(int code, String message, TokenDto tokenDto) {
            this.code = code;
            this.message = message;
            this.tokenDto = tokenDto;
        }

        public static UserLoginDto.Response of(TokenDto token) {
            LoginEnum loginEnum = token != null ? LoginEnum.SUCCESS : LoginEnum.FAIL;
            return UserLoginDto.Response.builder()
                    .code(loginEnum.getCode())
                    .message(loginEnum.getMessage())
                    .tokenDto(token)
                    .build();
        }
    }
}
