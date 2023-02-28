package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.utils.Script;
import com.cos.photogramstart.web.dto.CMRespDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController // 추가적인 데이터 응답을 할 수 있도록 설정
@ControllerAdvice // 모든 Exception이 이 Handler로 올 수 있게 해줌
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class) // RuntimeException이 발생하면 이곳으로 보내준다.
    public String runtimeException(CustomValidationException exception) {
        return Script.back(exception.getErrorMap().toString()); // 기존에 작성했던 CMRespDto는 개발자를 위한 것이라고 하면, Script는 클라이언트 입장에서 알림창이다.
    }
}
