package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EstadoDuplicadoException extends RuntimeException{

    public EstadoDuplicadoException(String nome) {
        super(" O Estado :" + nome + "está Duplicado");
    }
    
}
