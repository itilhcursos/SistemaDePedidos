package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FormaPagamentoDuplicadaException extends RuntimeException {

    public FormaPagamentoDuplicadaException(String nomeFormaPagamento) {
        super("A forma de pagamento " + nomeFormaPagamento + " já foi cadastrada!");
    }
}