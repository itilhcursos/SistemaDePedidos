package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FormaPagamentoDuplicadoException extends RuntimeException {
    public FormaPagamentoDuplicadoException(String descricao){
       super("Já Existe uma Forma de Pagamento '" + descricao + "' com a mesma descrição!");
    }

}
