package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FormaPagamentoDuplicadoException extends RuntimeException {

    public FormaPagamentoDuplicadoException(String nome){
        super("Forma de pagamento = " + nome + " duplicada.");
    }
}
