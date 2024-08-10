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

        assertEquals(true, Validador.isCpf("214.403.198-39"));

    }

    @Test
    @DisplayName("Teste CPF Invalido")
    void testIsCpfIsInvalido() {

        assertEquals(false, Validador.isCpf("544.545.545-54"));

    }

    @Test
    @DisplayName("teste para todos os digitos iguais")
    void testIsCpfDigitosIguais() {
        assertAll("CPFs",
                () -> assertEquals(false, Validador.isCpf("000.000.000-00")),
                () -> assertEquals(false, Validador.isCpf("111.111.111-11")),
                () -> assertEquals(false, Validador.isCpf("222.222.222-22")),
                () -> assertEquals(false, Validador.isCpf("333.333.333-33")),
                () -> assertEquals(false, Validador.isCpf("444.444.444-44")),
                () -> assertEquals(false, Validador.isCpf("555.555.555-55")),
                () -> assertEquals(false, Validador.isCpf("666.666.666-66")),
                () -> assertEquals(false, Validador.isCpf("777.777.777-77")),
                () -> assertEquals(false, Validador.isCpf("888.777.777-88")),
                () -> assertEquals(false, Validador.isCpf("999.999.999-99")));
    }

    @Test
    @DisplayName("Teste de letra no lugar de número")
    void testIsCpfNaoNumerico() {

        assertThrows(NumberFormatException.class, () -> Validador.isCpf("aghskaksoio"));

    }

    @Test
    @DisplayName("Teste de cpf com menos de 11 dígitos")
    void testIsCpfMenosDigitos() {

        assertEquals(false, Validador.isCpf("151.001"));

    }

    @Test
    @DisplayName("Teste de cpf com mais de 11 dígitos")
    void testIsCpfMaisDigitos() {

        assertEquals(false, Validador.isCpf("151.345.124.526-56-563"));

    }

    @Test
    @DisplayName("Teste CNPJ Valido")
    void testIsCnpjIsValido() {
        
        assertEquals(true, Validador.isCnpj("12.345.678/0001-00"));
    }

    @Test
    @DisplayName("Teste CNPJ Invalido")
    void testIsCnpjIsInvalido() {

        assertEquals(false, Validador.isCnpj("11.555.212/0001-20"));

    }
}
