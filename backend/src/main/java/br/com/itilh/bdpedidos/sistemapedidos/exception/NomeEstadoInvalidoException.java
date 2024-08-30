package br.com.itilh.bdpedidos.sistemapedidos.exception; 

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NomeEstadoInvalidoException extends  RuntimeException{

    public NomeEstadoInvalidoException(String nome){
        super("O nome :" + nome + " é invalido para o estado.");
    }

}
