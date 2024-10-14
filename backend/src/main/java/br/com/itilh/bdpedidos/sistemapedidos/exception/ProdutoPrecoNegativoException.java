package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProdutoPrecoNegativoException extends RuntimeException{

    public ProdutoPrecoNegativoException(String produto){
        super("O produto " + produto + " não pode ter o preço negativo.");
    }
}