package com.dhaudgkr.jwtsample.domain.token.exception;

import lombok.Builder;

public class InvalidRefreshTokenException extends RuntimeException {

    private String refreshToken;

    @Builder
    public InvalidRefreshTokenException(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public InvalidRefreshTokenException(String message, String refreshToken) {
        super(message);
        this.refreshToken = refreshToken;
    }

    public InvalidRefreshTokenException(String message, Throwable cause, String refreshToken) {
        super(message, cause);
        this.refreshToken = refreshToken;
    }
}
