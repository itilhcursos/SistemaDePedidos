package br.com.itilh.bdpedidos.sistemapedidos.exception;

import java.math.BigInteger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PedidoDuplicadoException extends RuntimeException {

    public PedidoDuplicadoException(BigInteger numero){
        super("O pedido nº" + numero + " já existe");
    }
}