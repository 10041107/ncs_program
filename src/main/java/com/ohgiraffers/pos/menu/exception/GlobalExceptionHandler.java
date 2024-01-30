package com.ohgiraffers.pos.menu.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.thymeleaf.exceptions.TemplateInputException;

import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.SQLSyntaxErrorException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NullPointerException.class)
    public String nullPointerExceptionHandler(NullPointerException e,Model model){
        System.out.println("global 레벨의 NullPointerException 처리");
        model.addAttribute("message", "비어있는 값이 존재합니다.");
        return "error/errorMessage";
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public String SQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e, Model model){
        model.addAttribute("message", "문제가 발생 했습니다. 다시 시도 해주세요");
        return "error/errorMessage";
    }

    @ExceptionHandler(SQLSyntaxErrorException.class)
    public String sqlSyntaxErrorExceptionHandler(SQLSyntaxErrorException e, Model model){
        model.addAttribute("message", "쿼리 작성문이 잘못 되었습니다.");
        return "error/errorMessage";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e,Model model){
        model.addAttribute("message", "메뉴 코드를 입력 해주세요.");
        return "error/errorMessage";
    }
    @ExceptionHandler(NotInsertNameException.class)
    public String notInsertNameExceptionHandler(NotInsertNameException e, Model model){
            model.addAttribute("message", "이미 있는 메뉴 이거나 공란 입니다.");
        return "error/errorMessage";
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public String noResourceFoundExceptionHandler(NoResourceFoundException e, Model model){
        model.addAttribute("message", "요청하신 페이지를 찾을 수 없습니다.");
        return "error/errorMessage";
    }

/*    @ExceptionHandler(Exception.class)
    public String nullPointerExceptionHandler(Exception e,Model model){
        System.out.println("exception 발생함");
        model.addAttribute("message", "오류가 발생했습니다.");
        return "error/errorMessage";
    }*/
}
