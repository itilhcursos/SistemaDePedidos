package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProdutoValorNegativoException extends RuntimeException {
    public ProdutoValorNegativoException(String descricao){
        super("Produto '" + descricao + "' Com Valor Informado Negativo!");
    }
}
