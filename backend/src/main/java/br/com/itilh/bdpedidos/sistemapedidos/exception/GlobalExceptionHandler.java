package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException( Exception e){
        String err = String.format("{\"exception\": \"%s\",\"class\": \"%s\", \"mensagem\":\"%s\"}",e.getClass().getSimpleName(), e.getClass().toString(), e.getMessage() );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

}
