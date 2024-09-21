package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CleinteDuplicadoException extends RuntimeException{
    public CleinteDuplicadoException(String cliente){
        super("O Cliente :" + cliente + " já existe");
    }
}
