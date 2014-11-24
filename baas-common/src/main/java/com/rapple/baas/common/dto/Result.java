package com.rapple.baas.common.dto;

/**
 * Created by libin on 14-11-23.
 */
public class Result<T> {
    private T Data;
    private String error;

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Result(T data) {
        Data = data;
    }

    public Result(String error,String reason) {
        this.error = error;
    }

}
