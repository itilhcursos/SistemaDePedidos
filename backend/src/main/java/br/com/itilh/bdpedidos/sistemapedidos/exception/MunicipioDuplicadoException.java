package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MunicipioDuplicadoException extends RuntimeException {

    public MunicipioDuplicadoException(String nome){
        super("Município :"+ nome + " duplicado.");
    }
}
