package com.example.cs2sw.config;

import com.example.cs2sw.dto.ErrorDTO;
import com.example.cs2sw.exception.CustomException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(value = {CustomException.class})
    @ResponseBody
    public ResponseEntity<ErrorDTO> handlerException(CustomException exception) {
        return ResponseEntity.status(exception.getCode())
                .body(ErrorDTO.builder().message(exception.getMessage()).build());
    }
}
