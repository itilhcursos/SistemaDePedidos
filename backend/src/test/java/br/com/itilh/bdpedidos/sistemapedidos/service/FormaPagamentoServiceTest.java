package br.com.itilh.bdpedidos.sistemapedidos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.dto.FormaPagamentoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.FormaPagamentoDuplicadoException;

@SpringBootTest
@ActiveProfiles("test")
public class FormaPagamentoServiceTest {

    @Autowired
    FormaPagamentoService formaPagamento;

    @Test
    @DisplayName("Test de crição de uma forma de pagamento")
    void testAlterarFormaPagamento() throws Exception {
        FormaPagamentoDTO testeDTO = new FormaPagamentoDTO(null, "formaPagamento de teste", null);
        FormaPagamentoDTO retorna = formaPagamento.criarFormaPagamento(testeDTO);
        assertEquals(true, retorna.getId() != null);

    }

    @Test
    @DisplayName("Test de crição de uma forma de pagamento duplicada")
    void testFormaPagamentoDuplicada() throws Exception {
        FormaPagamentoDTO testeDTO = new FormaPagamentoDTO(null, "formaPagamento duplicada de teste", null);
        FormaPagamentoDTO retorna = formaPagamento.criarFormaPagamento(testeDTO);
        assertThrows(FormaPagamentoDuplicadoException.class, () -> formaPagamento.criarFormaPagamento(testeDTO));

    }

}
