package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProdutoDuplicadoException extends RuntimeException {

    public ProdutoDuplicadoException(String descricao) {
        super("O produto:" + descricao + "já existe");
    }

}
