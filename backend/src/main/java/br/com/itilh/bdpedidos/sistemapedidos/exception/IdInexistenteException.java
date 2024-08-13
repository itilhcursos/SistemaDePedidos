package br.com.itilh.bdpedidos.sistemapedidos.exception;

import java.math.BigInteger;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IdInexistenteException extends RuntimeException {

    public IdInexistenteException (String nomeClasse, BigInteger id){
        super("O id =" + id.toString() + " da classe " + nomeClasse + " não foi encontrado");
    }

}
