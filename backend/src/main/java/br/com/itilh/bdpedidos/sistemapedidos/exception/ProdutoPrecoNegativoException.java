package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProdutoPrecoNegativoException extends RuntimeException {
    public ProdutoPrecoNegativoException(String Descricao){
        super("O Produto: " + Descricao + " não pode ter o Preço Negativo");
    }
}
