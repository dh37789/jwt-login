package com.dhaudgkr.jwtsample.domain.user.dto;

import com.dhaudgkr.jwtsample.domain.user.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserRegisterDto {

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

        public User toEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Response {

        private String username;

        @Builder
        public Response(String username) {
            this.username = username;
        }

    }
}
