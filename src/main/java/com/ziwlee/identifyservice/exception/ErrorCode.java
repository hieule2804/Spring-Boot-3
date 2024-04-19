package com.ziwlee.identifyservice.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"UnCategorized Exception"),
    USER_EXISTED(400,"User Existed"),
    USER_NOT_EXISTED(404,"User Not Existed"),
    KEY_INVALID(0001,"Invalid Message key"),
    USERNAME_INVALID(1003,"Username must be at least 3 character"),
    PASSWORD_INVALID(1004,"Password must be at least 8 character"),
    UNAUTHENTICATED(1006,"UnAuthenticated")

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
