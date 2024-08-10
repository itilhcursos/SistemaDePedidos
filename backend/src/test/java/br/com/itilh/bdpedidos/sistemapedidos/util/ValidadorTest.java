package br.com.itilh.bdpedidos.sistemapedidos.util;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ValidadorTest {
    @Test
    @DisplayName("teste CPF Válido")
    void testIsCpf() {
        assertEquals(true, Validador.isCpf("077.584.877-85"));
    }

    @Test
    @DisplayName("teste CPF inválido")
    void testIsCpfInvalido() {
        assertEquals(false, Validador.isCpf("000.584.877-85"));
    }

    @Test
    @DisplayName("teste CPF não numerico")
    void testIsCpfNaoNumerico() {
        assertThrows(NumberFormatException.class,()-> Validador.isCpf("sdsdsdsd"));
    }


    @Test
    @DisplayName("teste CPF com todos os numeros iguais")
    void testIsCpfNumeroIguais() {
        assertAll("CPFs",
         () -> assertEquals(false, Validador.isCpf("00000000000")),
         () -> assertEquals(false, Validador.isCpf("11111111111")),
         () -> assertEquals(false, Validador.isCpf("22222222222")),
         () -> assertEquals(false, Validador.isCpf("33333333333"))         
         );
    }


    @Test
    @DisplayName("teste CNPJ Válido")
    void testIsCnpj() {
        assertEquals(true, Validador.isCnpj("12.345.678/0001-00"));
    }

    @Test
    @DisplayName("teste CNPJ inválido")
    void testIsCnpjInvalido() {
        assertEquals(false, Validador.isCnpj("12.345.678/0001-12"));
    }

    @Test
    @DisplayName("teste CNPJ não numerico")
    void testIsCnpjNaoNumerico() {
        assertThrows(NumberFormatException.class,()-> Validador.isCnpj("sdsdsdsd"));
    }


    @Test
    @DisplayName("teste CNPJ com todos os numeros iguais")
    void testIsCnpjNumeroIguais() {
        assertAll("CPFs",
         () -> assertEquals(false, Validador.isCnpj("00000000000")),
         () -> assertEquals(false, Validador.isCnpj("11111111111")),
         () -> assertEquals(false, Validador.isCnpj("22222222222")),
         () -> assertEquals(false, Validador.isCnpj("33333333333"))         
         );
    }



}
