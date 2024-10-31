package br.com.itilh.bdpedidos.sistemapedidos.exception;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigInteger;

import org.springframework.http.HttpStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PedidoNumeroException extends RuntimeException {

    public PedidoNumeroException(BigInteger numero ){
        super("O número do pedido: " + numero + " já existe!");
    }
}






