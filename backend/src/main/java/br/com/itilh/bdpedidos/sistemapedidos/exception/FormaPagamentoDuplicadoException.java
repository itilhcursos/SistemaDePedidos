package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FormaPagamentoDuplicadoException extends RuntimeException{

    public FormaPagamentoDuplicadoException(String formaPagamento){
        super("A Forma de Pagamento: " + formaPagamento + " já existe!");
    }
}