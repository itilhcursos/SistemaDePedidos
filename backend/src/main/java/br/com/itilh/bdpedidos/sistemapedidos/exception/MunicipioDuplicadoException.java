package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MunicipioDuplicadoException extends RuntimeException {
    public MunicipioDuplicadoException (String nome){
        super("O muncípio  = " + nome + " já está casdastrado");
    }
}
