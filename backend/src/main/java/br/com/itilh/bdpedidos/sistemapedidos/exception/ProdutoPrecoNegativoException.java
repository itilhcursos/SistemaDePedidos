package br.com.itilh.bdpedidos.sistemapedidos.exception;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProdutoPrecoNegativoException extends RuntimeException {
    
    public ProdutoPrecoNegativoException(BigDecimal PrecoUnidadeAtual){
        super("O Preço = " + PrecoUnidadeAtual + ": Preço Negativo");
    }
}
