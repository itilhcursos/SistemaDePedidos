package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CpfDuplicadoException extends RuntimeException{

    public CpfDuplicadoException(String cpf){
        super("O CPF :" + cpf + " já existe");
    }
}
