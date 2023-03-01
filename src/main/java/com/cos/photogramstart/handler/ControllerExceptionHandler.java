package com.cos.photogramstart.handler;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.utils.Script;
import com.cos.photogramstart.web.dto.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController // 추가적인 데이터 응답을 할 수 있도록 설정
@ControllerAdvice // 모든 Exception이 이 Handler로 올 수 있게 해줌
public class ControllerExceptionHandler {

    // JavaScript 응답하는 핸들러
    @ExceptionHandler(CustomValidationException.class) // RuntimeException이 발생하면 이곳으로 보내준다.
    public String validationExcpetion(CustomValidationException exception) {
        // CMRespDto와 Script와 비교
        // 1. 클라이언트에게 응답 할 때는 Script가 좋음
        // 2. AJAX 통신을 하거나 안드로이드와 통신을 하게 되면 CMRespDto가 좋다.
        // 즉, 개발자를 위한 응답에는 CMRespDto, 클라이언트를 위해서는 Script가 좋다.
        return Script.back(exception.getErrorMap().toString()); // 기존에 작성했던 CMRespDto는 개발자를 위한 것이라고 하면, Script는 클라이언트 입장에서 알림창이다.
    }

    // CMRespDto 오브젝트 + HttpStatus 상태코드를 응답하는 핸들러
    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<CMRespDto<?>> validationApiException(CustomValidationApiException exception) {
        return new ResponseEntity<>(
                new CMRespDto<>(-1, exception.getMessage(), exception.getErrorMap()),
                HttpStatus.BAD_REQUEST);
        // 응답을 ErrorMap이 아닌 Http 상태코드로 반환해주기 위한 코드
    }
}
