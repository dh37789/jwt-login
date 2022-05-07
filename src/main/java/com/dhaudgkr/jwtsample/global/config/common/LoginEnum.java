package com.dhaudgkr.jwtsample.global.config.common;

import lombok.Getter;

@Getter
public enum LoginEnum {
    SUCCESS(0, "로그인에 성공했습니다."),
    FAIL(1, "로그인에 실패했습니다.");

    private int code;
    private String message;

    LoginEnum (int code, String message) {
        this.code = code;
        this.message = message;
    }
}
