package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProdutoEstoqueNegativoException extends RuntimeException{

    public ProdutoEstoqueNegativoException(String produto){
        super("O produto :" + produto + " nãp pode ter o Estoque negativo.");
    }
}


