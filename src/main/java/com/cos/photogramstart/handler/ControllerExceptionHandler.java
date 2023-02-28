package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController // 추가적인 데이터 응답을 할 수 있도록 설정
@ControllerAdvice // 모든 Exception이 이 Handler로 올 수 있게 해줌
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class) // RuntimeException이 발생하면 이곳으로 보내준다.
    public Map<String, String> runtimeException(CustomValidationException exception) {
        return exception.getErrorMap(); // Map이 RestController에 의해서 Json 형태로 반환이 된다.
    }
}
