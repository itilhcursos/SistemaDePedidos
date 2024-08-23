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
    void testIsCpfIsInValido() {
        assertEquals(false, Validador.isCpf("151.555.212-20"));
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
                () -> assertEquals(false, Validador.isCpf("888.888.888-88")),
                () -> assertEquals(false, Validador.isCpf("999.999.999-99")));
    }

    @Test
    @DisplayName("teste de letras no lugar de numero")
    void testIsCpfNaoNumerico() {
        assertThrows(NumberFormatException.class, () -> Validador.isCpf("isglsaglxaucksa"));
    }

    @Test
    @DisplayName("teste de cpf com menos de 11 digitos")
    void testIsCpfMenosDigitos() {
        assertEquals(false, Validador.isCpf("151.001"));
    }

    @Test
    @DisplayName("teste de cpf com Mais de 11 digitos")
    void testIsCpfMaisDigitos() {
        assertEquals(false, Validador.isCpf("151.001.515.545-545-545"));
    }

    ////// ================================== CNPJ
    ////// ====================================
    @Test
    @DisplayName(" TESTE para CNPJ Valido")
    void testIsCnpjIsValido() {
        assertEquals(true, Validador.isCnpj("12.345.678/0001-00"));
    }

    @Test
    @DisplayName("Teste CPF Inválido")
    void testIsCnpjIsInValido() {
        assertEquals(false, Validador.isCnpj("11.555.212/0001-20"));
    }

    @Test
    @DisplayName("teste para todos os digitos iguais")
    void testIsCnpjfDigitosIguais() {
        assertAll("CNPJs",
                () -> assertEquals(false, Validador.isCnpj("00.000.000/0000-00")),
                () -> assertEquals(false, Validador.isCnpj("11.111.111/1111-11")),
                () -> assertEquals(false, Validador.isCnpj("22.222.222/2222-22")),
                () -> assertEquals(false, Validador.isCnpj("33.333.333/3333-33")),
                () -> assertEquals(false, Validador.isCnpj("44.444.444/4444-44")),
                () -> assertEquals(false, Validador.isCnpj("55.555.555/5555-55")),
                () -> assertEquals(false, Validador.isCnpj("66.666.666/6666-66")),
                () -> assertEquals(false, Validador.isCnpj("77.777.777/7777-77")),
                () -> assertEquals(false, Validador.isCnpj("88.888.888/8888-88")),
                () -> assertEquals(false, Validador.isCnpj("99.999.999/9999-99")));
    }

    @Test
    @DisplayName("teste de letras no lugar de numero")
    void testIsCnpjNaoNumerico() {
        assertThrows(NumberFormatException.class, () -> Validador.isCnpj("isglsaglxaucksa"));
    }

    @Test
    @DisplayName("teste de cpf com menos de 14 digitos")
    void testIsCnpjMenosDigitos() {
        assertEquals(false, Validador.isCnpj("151.001"));
    }

    @Test
    @DisplayName("teste de cnpj com Mais de 15 digitos")
    void testIsCnpjMaisDigitos() {
        assertEquals(false, Validador.isCpf("151.001.515./0001-545-545"));
    }

}
