package com.cos.photogramstart.handler.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component // IoC 컨테이너에 빈으로 등록해주는 어노테이션들의 최상위 객체
@Aspect // AOP 처리를 할 수 있도록 해주는 어노테이션
public class ValidationAdvice {
    // @Before: 어떤 메서드가 실행되기 직전에 한번 실행되게 해주는 어노테이션
    // @Around: 어떤 메서드의 실행 전부터 실행 후까지 관하는 어노테이션 => 전처리, 후처리를 모두 관여하기 때문에 이걸 사용
    // @After: 어떤 메서드가 종료되고 나서 한번 실행되게 해주는 어노테이션


    // API를 위한 응답
    @Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))")
    // * : public, private, protected와 같은 접근제한자를 지정하는 부분
    // com.cos.photogramstart.web.api : 적용될 패키지 경로
    // *Controller: 파일명이 Controller로 끝나는 파일들을 지정
    // *(..) : 해당 경로에 있는 메서드를 지정
    // 즉, com.cos.photogramstart.web.api 경로에 있는 Controller 안에 있는 모든 메서드들이 실행되기 전과 후 처리르 여기서 해준다.
    public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // ProceedingJoinPoint는 execution에 지정된 경로에 있는 모든 메서드에 대한 정보에 접근할 수 있는 인터페이스이다.
        // Throwable은 Excpetion이 상속받고 있는 객체이다.

        System.out.println("apiAdvice 실행");

        return proceedingJoinPoint.proceed(); // proceed()는 다시 원래 메서드로 돌아갈 수 있도록 해준다.
    }

    // Html을 위한 응답
    @Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
    public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        System.out.println("advice 실행");

        return proceedingJoinPoint.proceed();
    }
}
