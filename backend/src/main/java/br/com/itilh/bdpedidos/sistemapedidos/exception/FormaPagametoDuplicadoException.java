package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FormaPagametoDuplicadoException extends RuntimeException {

    public FormaPagametoDuplicadoException(String descricao) {
        super("A forma de pagamento:" + descricao + "já existe");
    }

}
