package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProdutoDuplicadoException extends RuntimeException{

    public ProdutoDuplicadoException(String nome){
        super("O produto: " + nome + " já foi cadastrado");
    }
}
