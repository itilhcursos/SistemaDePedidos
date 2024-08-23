package br.com.itilh.bdpedidos.sistemapedidos.exception;

<<<<<<< HEAD
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
=======
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
>>>>>>> develop

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MunicipioDuplicadoException extends RuntimeException {

<<<<<<< HEAD
    public MunicipioDuplicadoException(String nome) {
        super("Municipio = " + nome + "Duplicado");
=======
    public MunicipioDuplicadoException(String nome){
        super("Município = "+ nome + " duplicado.");
>>>>>>> develop
    }
}
