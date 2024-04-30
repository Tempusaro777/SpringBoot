package com.example.demo.model;

public class Result<T> {
    private int statusCode;
    private String message;
    private T data;

    public Result() {
    }

    public Result(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public static <E> Result<E> success(E data) {
        return new Result<>(0, "Success!", data);
    }

    public static <E> Result<E> success() {
        return new Result<>(0, "Success!", null);
    }

    public static <E> Result<E> error(String msg) {
        return new Result<>(1, msg, null);
    }

    public static <E> Result<E> error(int statusCode, String msg) {
        return new Result<>(statusCode, msg, null);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
