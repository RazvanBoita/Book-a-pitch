package com.book_a_pitch.bapAPI.common.responses;

import lombok.Getter;

@Getter
public class Result<T> {
    @Getter
    private boolean success;
    private T data;
    private String errorMessage;

    public Result(T data) {
        this.success = true;
        this.data = data;
        this.errorMessage = null;
    }

    public Result(String errorMessage) {
        this.success = false;
        this.data = null;
        this.errorMessage = errorMessage;
    }
}
