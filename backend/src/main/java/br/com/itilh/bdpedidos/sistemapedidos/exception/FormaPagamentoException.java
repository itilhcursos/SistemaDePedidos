package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;


// deveria ser FormaPagamentoDuplicadoExcepition
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FormaPagamentoException extends RuntimeException {

    public FormaPagamentoException(String descricao){
        super("Pagamento "+ descricao + " esta duplicado.");
    }
}