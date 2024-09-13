package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
//(HttpStatus.BAD_REQUEST)
@ResponseStatus(HttpStatus.CONFLICT)
public class ProdutoDuplicadoException extends RuntimeException {

    public ProdutoDuplicadoException(String nome) {
        super("Produto com o nome = " + nome + " já existe.");
    }
}