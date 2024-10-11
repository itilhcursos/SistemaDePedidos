package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;



@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FormaPagamentoException extends RuntimeException{

    public FormaPagamentoException(String nomeEstado){
        super("O estado :" + nomeEstado + " já existe");
    }
}
