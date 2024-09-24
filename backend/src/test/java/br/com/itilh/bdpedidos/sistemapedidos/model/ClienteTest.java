package br.com.itilh.bdpedidos.sistemapedidos.model;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

public class ClienteTest {

    @Test
    public void testeConstrutor(){

        Estado e1 = new Estado(BigInteger.ONE, "Estado Teste");
        Municipio m1 = new Municipio(BigInteger.ONE, "Municipio teste",Boolean.TRUE, e1);
        
        Cliente cliente = new Cliente(BigInteger.ONE, m1, "Razao","CNPJ", null, "000","rua", "bairro","cep", "e-mail", Boolean.TRUE,"info");




    }
}
