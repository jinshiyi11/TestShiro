package com.example.shiro.response;


/**
 *
 */
public class ResponseInfo<T> {
    private int mErrorCode = ErrorCode.ERROR_SUCCESS.getErrorCode();

    //@JsonProperty("msg")
    private String mMessage;

    private T mData;

    public ResponseInfo() {
    }

    public ResponseInfo(int errorCode, String message) {
        mErrorCode = errorCode;
        this.mMessage = message;
    }

    public ResponseInfo(ErrorCode error) {
        mErrorCode = error.getErrorCode();
        mMessage = error.getMessage();
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public void setErrorCode(int errorCode) {
        mErrorCode = errorCode;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        this.mData = data;
    }
}
