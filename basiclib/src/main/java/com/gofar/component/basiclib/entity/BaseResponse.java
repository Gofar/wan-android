package com.gofar.component.basiclib.entity;

/**
 * @author lcf
 * @date 2018/4/20 14:03
 * @since 1.0
 */
public class BaseResponse<T> {
    /**
     * 请求成功返回码
     */
    public static final int SUCCESS_CODE = 0;

    private T data;
    private int errorCode;
    private String errorMsg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * 是否请求成功
     * @return
     */
    public boolean isSuccess() {
        return errorCode == SUCCESS_CODE;
    }
}
