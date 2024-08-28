package br.com.itilh.bdpedidos.sistemapedidos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
/* import static org.junit.jupiter.api.Assertions.assertThrows; */

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.dto.FormaPagamentoDTO;
/* import br.com.itilh.bdpedidos.sistemapedidos.exception.FormaPagamentoDuplicadaException; */

@SpringBootTest
@ActiveProfiles("test")
public class FormaPagamentoServiceTest {

    @Autowired
    FormaPagamentoService formaPagamentoService;

    @Test
    void testCriarFormaPagamento() throws Exception {
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null,"FormaPagamento",true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);
        assertEquals(true, dtoRetorno.getId() != null);
    }

    @Test
    @DisplayName("Teste de regra de negócio -  FormaPagamento Duplicada")
    void testCriarFormaPagamentoDuplicada() throws Exception {
        /* FormaPagamentoDTO dto = new FormaPagamentoDTO(null,"FormaPagamento",true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);
        assertThrows(FormaPagamentoDuplicadaException.class, ()-> formaPagamentoService.criarFormaPagamento(dto)); */
    }

    @Test
    @DisplayName("Teste de alteração de descrição")
    void testCriarFormaPagamentoAlterar() throws Exception {}
}