package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EstadoDuplicadoException extends RuntimeException{

    public EstadoDuplicadoException(String nomeEstado){
        super("O estado :" + nomeEstado + " já existe");
    }
}
