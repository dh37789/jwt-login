package com.dhaudgkr.jwtsample.global.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    USERNAME_ALREADY_EXISTS("001", "유저이름이 중복되었습니다.", 400),
    USER_NOT_ACTIVATED("002", "회원 권한이 옳지 않습니다.", 400),
    USER_NOT_FOUND("003", "해당 회원을 찾을 수 없습니다.", 400),

    TOKEN_EXPIRE("101", "만료된 토큰", 401),
    UNSUPPORTED_TOKEN("102", "지원하지 않는 토큰", 401);

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(String code, String message, int status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

}
