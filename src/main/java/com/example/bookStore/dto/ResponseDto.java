package com.example.bookStore.dto;

public class ResponseDto<T> {
    private T data;
    private String errorMessage;

    public ResponseDto(T data, String errorMessage) {
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String error) {
        this.errorMessage = error;
    }
}