package br.com.itilh.bdpedidos.sistemapedidos.exception;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ItemPedidoPrecoNegativoException extends RuntimeException{


    public ItemPedidoPrecoNegativoException(BigDecimal  precoUnidadeAtual){

        super("ItemPedido = " +  precoUnidadeAtual + "Negativo");
    }
}
