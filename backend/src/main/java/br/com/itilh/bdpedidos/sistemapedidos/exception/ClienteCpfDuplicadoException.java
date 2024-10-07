package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClienteCpfDuplicadoException extends RuntimeException {

    public ClienteCpfDuplicadoException(String cpf) {
        super("O número de CPF: " + cpf + " já existe no sistema.");
    }
}
