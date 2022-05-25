package com.dhaudgkr.jwtsample.domain.token.exception;

import lombok.Builder;

public class NotMatchedUserName extends RuntimeException {
    private String refreshToken;

    @Builder
    public NotMatchedUserName(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public NotMatchedUserName(String message, String refreshToken) {
        super(message);
        this.refreshToken = refreshToken;
    }

    public NotMatchedUserName(String message, Throwable cause, String refreshToken) {
        super(message, cause);
        this.refreshToken = refreshToken;
    }
}
