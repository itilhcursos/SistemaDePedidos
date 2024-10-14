package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CnpjDuplicadoException extends RuntimeException{
    public CnpjDuplicadoException(String cnpj){
        super("O CNPJ :" + cnpj + " já existe");
    }
}
