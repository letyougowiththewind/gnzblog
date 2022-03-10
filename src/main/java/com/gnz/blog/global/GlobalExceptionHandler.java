package com.gnz.blog.global;

import com.gnz.blog.exception.BlogException;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.*;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import com.gnz.blog.model.enums.*;
import com.gnz.blog.model.vo.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Results noHandlerFoundExceptionHandler(NoHandlerFoundException exception) {
        log.error("NoHandlerFoundException:{}", exception.getMessage());
        return Results.fromErrorInfo(ErrorInfoEnum.RESOURCES_NOT_FOUND);
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Results exceptionHandler(Exception exception) {
        exception.printStackTrace();
        log.error("Exception:{}", exception.getMessage());
        return Results.fromErrorInfo(ErrorInfoEnum.UNKNOWN_ERROR);
    }
    @ResponseBody
    @ExceptionHandler(value = BlogException.class)
    @ResponseStatus(HttpStatus.OK)
    public Results blogExceptionHandler(BlogException exception) {
        log.error("BlogException:{}", exception.getMessage());
        return exception.toResultVO();
    }
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    public Results handleValidationExceptions(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return Results.error("参数错误", errors);
    }
    @ResponseBody
    @ExceptionHandler(value = SignatureException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Results signatureExceptionHandler(SignatureException exception) {
        log.error("SignatureException:{}", exception.getMessage());
        return Results.fromErrorInfo(ErrorInfoEnum.TOKEN_INVALID);
    }


}
