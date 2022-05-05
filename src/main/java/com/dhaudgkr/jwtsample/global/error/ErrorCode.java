package com.dhaudgkr.jwtsample.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    LOGIN_FAIL("001", "해당 회원을 찾을 수 없습니다.", 404),
    USERNAME_ALREADY_EXISTS("002", "유저이름이 중복되었습니다.", 400);

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

}
