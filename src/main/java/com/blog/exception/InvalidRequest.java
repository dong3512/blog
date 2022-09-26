package com.blog.exception;

import lombok.Getter;

@Getter
public class InvalidRequest extends BlogException{

    private static final String MESSAGE = "잘못된 요청입니다.";

    public InvalidRequest() {
        super(MESSAGE);
    }

    public InvalidRequest(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName , message);
    }

    @Override
    public int getSatatusCode() {
        return 400;
    }


}
