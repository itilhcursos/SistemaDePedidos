package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class ProdutoEstoqueNegativoException extends RuntimeException {


    public ProdutoEstoqueNegativoException(Double quantidadeEstoque){

        super("A quantidade:" + quantidadeEstoque + "não pode ser menor que zero");

    }

    
}
