package br.com.itilh.bdpedidos.sistemapedidos.exception;

import java.math.BigInteger;

public class IdInexistenteException  extends RuntimeException {

    public IdInexistenteException(String nomeClasse, BigInteger id){
        super("O id= " + id.toString() + " da classe " + nomeClasse + " não foi encontrado.");
    }
}
