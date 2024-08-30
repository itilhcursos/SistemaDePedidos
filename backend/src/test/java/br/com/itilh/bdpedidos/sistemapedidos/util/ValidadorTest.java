package br.com.itilh.bdpedidos.sistemapedidos.util;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ValidadorTest {
    @Test
    @DisplayName("Teste CPF Válido")         
    void testIsCpfIsValido() {
        assertEquals(true, Validador.isCpf("077.584.877-85"));
    }

    @Test
    @DisplayName("Teste CPF Inválido")
    void testIsCpfIsInValido(){
        assertEquals(false, Validador.isCpf("151.555.212-20"));
    }

    @Test
    @DisplayName("Teste Para Todos os Digitos Iguais")
    void testIsCpfDigitosIguais(){
        assertAll("CPFs",
             ()->  assertEquals(false, Validador.isCpf("000.000.000-00")),
             ()->  assertEquals(false, Validador.isCpf("111.111.111-11")),
             ()->  assertEquals(false, Validador.isCpf("222.222.222-22")),
             ()->  assertEquals(false, Validador.isCpf("333.333.333-33")), 
             ()->  assertEquals(false, Validador.isCpf("444.444.444-44")),
             ()->  assertEquals(false, Validador.isCpf("555.555.555-55")),
             ()->  assertEquals(false, Validador.isCpf("666.666.666-66")),
             ()->  assertEquals(false, Validador.isCpf("777.777.777-77")),
             ()->  assertEquals(false, Validador.isCpf("888.888.888-88")),
             ()->  assertEquals(false, Validador.isCpf("999.999.999-99"))
            );
    }

    @Test
    @DisplayName("Teste de Letras no Lugar de Número")
    void testIsCpfNaoNumerico(){
        assertThrows(NumberFormatException.class, ()->Validador.isCpf("abcdefghijklmnopqrstuvwxyz"));
    }
    
    @Test
    @DisplayName("Teste de CPF Com Menos de 11 Digitos")
    void testIsCpfMenosDigitos(){
        assertEquals(false, Validador.isCpf("151.001"));
    }


    @Test
    @DisplayName("Teste de CPF Com Mais de 11 Digitos")
    void testIsCpfMaisDigitos(){
        assertEquals(false, Validador.isCpf("151.001.515.545-545-545"));
    }

   //////   ================================== CNPJ ====================================
    @Test
    @DisplayName(" Teste Para CNPJ Válido")
    void testIsCnpjIsValido(){
        assertEquals(true, Validador.isCnpj("84.401.848/0001-12"));
    }


    @Test
    @DisplayName("Teste CNPJ Inválido")
    void testIsCnpjIsInValido(){
        assertEquals(false, Validador.isCnpj("11.555.212/0001-20"));
    }

    @Test
    @DisplayName("Teste Para Todos os Digitos Iguais")
    void testIsCnpjfDigitosIguais(){
        assertAll("CNPJs",
             ()->  assertEquals(false, Validador.isCnpj("00.000.000/0000-00")),
             ()->  assertEquals(false, Validador.isCnpj("11.111.111/1111-11")),
             ()->  assertEquals(false, Validador.isCnpj("22.222.222/2222-22")),
             ()->  assertEquals(false, Validador.isCnpj("33.333.333/3333-33")),
             ()->  assertEquals(false, Validador.isCnpj("44.444.444/444-44")),
             ()->  assertEquals(false, Validador.isCnpj("55.555.555/555-55")),
             ()->  assertEquals(false, Validador.isCnpj("66.666.666/666-66")),
             ()->  assertEquals(false, Validador.isCnpj("77.777.777/777-77")),
             ()->  assertEquals(false, Validador.isCnpj("88.888.888/888-88")),
             ()->  assertEquals(false, Validador.isCnpj("99.999.999/999-99"))
            );
    }

    @Test
    @DisplayName("Teste de Letras no Lugar de Número")
    void testIsCnpjNaoNumerico(){
        assertThrows(NumberFormatException.class, ()->Validador.isCnpj("abcdefghijklmnopqrstuvwxyz"));
    }

    @Test
    @DisplayName("Teste de CPF Com Menos de 14 Digitos")
    void testIsCnpjMenosDigitos(){
        assertEquals(false, Validador.isCpf("151.001"));
    }


    @Test
    @DisplayName("Teste de CNPJ Com Mais de 15 Digitos")
    void testIsCnpjMaisDigitos(){
        assertEquals(false, Validador.isCpf("151.001.5150001545-545"));
    } 
    
    
    
}
