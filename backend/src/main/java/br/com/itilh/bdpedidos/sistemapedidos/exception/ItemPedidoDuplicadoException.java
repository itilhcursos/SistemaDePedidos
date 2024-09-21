package br.com.itilh.bdpedidos.sistemapedidos.exception;

import java.math.BigInteger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ItemPedidoDuplicadoException extends RuntimeException  {

    public ItemPedidoDuplicadoException(BigInteger pedidoId){

        super("ItemPedido = " + pedidoId + "duplicado");
    }

}
