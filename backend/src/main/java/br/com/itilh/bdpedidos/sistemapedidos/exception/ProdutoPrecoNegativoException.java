package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProdutoPrecoNegativoException extends RuntimeException {

    public ProdutoPrecoNegativoException() {
        super("O preço do produto não pode ser negativo.");
    }
}