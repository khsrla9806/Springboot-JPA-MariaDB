package com.cos.photogramstart.handler.ex;

import java.util.Map;

// ajax, android 통신에서는 Script가 아닌 CMRespDto로 응답하는 것이 좋기 때문에 따로 예외를 또 만드는 것이다.
public class CustomValidationApiException extends RuntimeException {
    // 객체를 구별하기 위해서 시리얼 넘버를 넣어준다.
    private static final long serialVersionUid = 1L;

    private Map<String, String> errorMap;

    public CustomValidationApiException(String message, Map<String, String> errorMap) {
        super(message); // RuntimeException에 메시지를 전달
        this.errorMap = errorMap;
    }

    public CustomValidationApiException(String message) {
        super(message);
    }

    public Map<String, String> getErrorMap() {
        return errorMap;
    }
}
