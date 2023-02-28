package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.web.dto.CMRespDto;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController // 추가적인 데이터 응답을 할 수 있도록 설정
@ControllerAdvice // 모든 Exception이 이 Handler로 올 수 있게 해줌
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class) // RuntimeException이 발생하면 이곳으로 보내준다.
    public CMRespDto<?> runtimeException(CustomValidationException exception) {
        return new CMRespDto<>(-1, exception.getMessage(), exception.getErrorMap()); // 동적으로 예외 데이터를 얻어오기 위해서 제너릭에 <> 타입을 지정하지 않는다.
    }
}
