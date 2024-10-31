package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClienteCnpjDuplicadoException extends RuntimeException {

    public ClienteCnpjDuplicadoException(String cnpj) {
        super("O número de CNPJ: " + cnpj + " já existe no sistema!");
    }

}
