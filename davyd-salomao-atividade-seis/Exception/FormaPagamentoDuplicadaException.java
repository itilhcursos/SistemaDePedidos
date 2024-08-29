package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FormaPagamentoDuplicadaException extends RuntimeException {

    public FormaPagamentoDuplicadaException(String descricao) {
        super("Já Existe uma Forma de Pagamento com " + descricao + " duplicada!");
    }
}