package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProdutoDuplicadoException extends RuntimeException {

    public ProdutoDuplicadoException(String descricao){
        super("O Produto :" + descricao + " já existe");
    }

}
