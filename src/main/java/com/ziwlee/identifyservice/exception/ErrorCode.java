package com.ziwlee.identifyservice.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"UnCategorized Exception"),
    USER_EXISTED(400,"User Existed"),
    KEY_INVALID(0001,"Invalid Message key"),
    USERNAME_INVALID(1003,"Username must be at least 3 character"),
    PASSWORD_INVALID(1004,"Password must be at least 8 character")
    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
