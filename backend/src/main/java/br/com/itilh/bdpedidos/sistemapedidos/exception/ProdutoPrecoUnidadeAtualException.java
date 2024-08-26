package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import java.math.BigDecimal;
import org.springframework.http.HttpStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProdutoPrecoUnidadeAtualException extends RuntimeException {
    public ProdutoPrecoUnidadeAtualException(BigDecimal precoUnidadeAtual) {
        super("Preço da Unidade Atual = " + precoUnidadeAtual + ", preço não pode ser negativo.");
    }
    
}
