package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClienteDuplicadoException extends RuntimeException {
    public ClienteDuplicadoException(String nome){
        super("Cliente "+ nome + " já existe vinculado a esse município.");
    }    

}
