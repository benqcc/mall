package com.mallall.common;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.io.Serializable;

/**
 * @program: mall
 * @author: qcc
 * @description Result
 * @create: 2019-02-17 19:59
 * @Version: 1.0
 **/

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
//保证序列化json的时候,如果 是null的对象,key也会消失
public class Result<T> implements Serializable {

    private int status;
    private String msg;
    private T data;

    private Result(int status) {
        this.status = status;
    }

    private Result(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private Result(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private Result(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    @JsonIgnore
    //使之不在json序列化结果当中
    public boolean isSuccess() {
        return this.status == StatusCode.SUCCESS.getCode();
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }


    public static <T> Result<T> createBySuccess() {
        return new Result<>(StatusCode.SUCCESS.getCode());
    }

    public static <T> Result<T> createBySuccessMessage(String msg) {
        return new Result<>(StatusCode.SUCCESS.getCode(), msg);
    }

    public static <T> Result<T> createBySuccess(T data) {
        return new Result<>(StatusCode.SUCCESS.getCode(), data);
    }

    public static <T> Result<T> createBySuccess(String msg, T data) {
        return new Result<>(StatusCode.SUCCESS.getCode(), msg, data);
    }


    public static <T> Result<T> createByError() {
        return new Result<>(StatusCode.ERROR.getCode(), StatusCode.ERROR.getDesc());
    }


    public static <T> Result<T> createByErrorMessage(String errorMessage) {
        return new Result<>(StatusCode.ERROR.getCode(), errorMessage);
    }

    public static <T> Result<T> createByErrorCodeMessage(int errorCode, String errorMessage) {
        return new Result<>(errorCode, errorMessage);
    }


}

