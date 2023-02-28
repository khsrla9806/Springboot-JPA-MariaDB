package com.cos.photogramstart.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CMRespDto<T> { // 응답을 목적으로 하는 Dto 클래스를 생성
    private int code; // 유효성 검사에 성공하면 1, 실패하면 -1 (우리가 정하는 Code 약속)
    private String message;
    private T data; // 들어오는 타입에 따라서 동적으로 예외 데이터를 받아올 수 있게 설정
}
