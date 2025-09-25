package com.example.crewstation.common.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MemberLoginFailException.class)
    public RedirectView handleMemberLoginFailException(MemberLoginFailException e) {
        return new RedirectView("/member/login");
    }
}
