package com.dhaudgkr.jwtsample.global.config.common;

import lombok.Getter;

@Getter
public enum RegisterEnum {
    SUCCESS(0, "회원가입에 성공했습니다."),
    FAIL(1, "회원가입에 실패했습니다.");

    private int code;
    private String message;

    RegisterEnum (int code, String message) {
        this.code = code;
        this.message = message;
    }
}
