package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProdutoQuantidadeEstoqueException extends RuntimeException {
    public ProdutoQuantidadeEstoqueException(Double quantidadeEstoque) {
        super("Quantidade em Estoque = " + quantidadeEstoque + ", estoque não pode ser negativo.");
    }
    
}
