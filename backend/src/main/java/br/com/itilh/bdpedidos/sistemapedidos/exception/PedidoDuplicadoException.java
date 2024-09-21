package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PedidoDuplicadoException extends RuntimeException{

    public PedidoDuplicadoException(Integer numero){

        super("Pedido = " + numero + "duplicado");
    }

}
