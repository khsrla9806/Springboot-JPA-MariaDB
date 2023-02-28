package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomValidationException extends RuntimeException {

    // 객체를 구별하기 위해서 시리얼 넘버를 넣어준다.
    private static final long serialVersionUid = 1L;

    private Map<String, String> errorMap;

    public CustomValidationException(String message, Map<String, String> errorMap) {
        super(message); // RuntimeException에 메시지를 전달
        this.errorMap = errorMap;
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}
