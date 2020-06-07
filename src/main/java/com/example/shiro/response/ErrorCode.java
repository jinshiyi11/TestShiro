package com.example.shiro.response;

/**
 *
 */
public enum ErrorCode {
    ERROR_UNKNOWN(-1, "unknown error"),
    ERROR_SUCCESS(0, "success"),
    // 拒绝访问
    ERROR_ACCESS_DENY(1, "error access deny"),
    // 参数不正确
    ERROR_INVALID_PARAM(2, "参数不正确"),
    // 用户名或密码错误
    ERROR_USERNAME_OR_PASSWORD_ERROR(3, "用户名或密码错误"),
    // 该用户已被禁用
    ERROR_ACCOUNT_LOCKED(4, "该用户已被禁用"),
    // 用户已存在
    ERROR_USER_ALREADY_EXIST(5, "用户已存在"),
    // 发送验证码失败
    ERROR_SEND_VERIFICATION_CODE(6, "send verification code fail"),
    // 验证码不正确
    ERROR_INVALID_VERIFICATION_CODE(7, "invalid verification code");

    private int mErrorCode;
    private String mMessage;

    ErrorCode(int errorCode, String message) {
        this.mErrorCode = errorCode;
        this.mMessage = message;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(int errorCode) {
        this.mErrorCode = errorCode;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }
}
