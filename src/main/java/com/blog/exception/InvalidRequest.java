package com.blog.exception;

public class InvalidRequest extends BlogException{

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }

    @Override
    public int getSatatusCode() {
        return 400;
    }


}
