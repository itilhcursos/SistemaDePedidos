package br.com.itilh.bdpedidos.sistemapedidos.exception; 

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NomeMunicipioInvalidoException extends  RuntimeException{

    public NomeMunicipioInvalidoException(String nome){
        super("O nome :" + nome + " é invalido para o Municipio.");
    }

}
