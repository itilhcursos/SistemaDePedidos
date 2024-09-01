package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProdutoPrecoNegativoException extends RuntimeException {


    public ProdutoPrecoNegativoException(BigDecimal precoUnidadeAtual) {

        super("O preco:" + precoUnidadeAtual + "não pode ser menor que zero");
        
    }


}
