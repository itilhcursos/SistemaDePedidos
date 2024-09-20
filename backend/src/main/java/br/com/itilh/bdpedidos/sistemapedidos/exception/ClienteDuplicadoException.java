package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClienteDuplicadoException extends RuntimeException{

    public ClienteDuplicadoException(String nomeCliente){
        super("O cliente :" + nomeCliente + " já existe");
    }
}
