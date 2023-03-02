package com.cos.photogramstart.handler.ex;

// 메시지만 반환하는 일반적인 예에처리
public class CustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CustomException(String message) {
        super(message);
    }
}
