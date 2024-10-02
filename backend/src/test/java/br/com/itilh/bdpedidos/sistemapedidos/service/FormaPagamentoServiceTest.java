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
    EstadoService service;

    @Test
    void testAlterarFormaPagamento() {

    }

    @Test
    @DisplayName("Test de Criar FormaPagamento")
    void                                                                                                                                testCriarFormaPagamento(FormaPagamentoDTO retorno) throws Exception {

        FormaPagamentoDTO testeDTO = new FormaPagamentoDTO(null, "FormaPagamento de teste", null);
        FormaPagamentoDTO retono = service.testCriarFormaPagamento(testeDTO);
        assertEquals(true, retorno.getId()!=null);
    }

    @Test
    @DisplayName("Test de criação de uma FormaPagamento duplicada")
    void testFormaPagamentoDuplicado() throws Exception {

        FormaPagamentoDTO testeDTO = new FormaPagamentoDTO(null, "Teste formaPagamento duplicada", null);

        FormaPagamentoDTO retorno = service.testCriarFormaPagamento(testeDTO);

        assertThrows(FormaPagamentoDuplicadoException.class, ()-> service.testCriarFormaPagamento(testeDTO));
    }

    @Test
    void testDeletePorId() {

    }

    @Test
    void testGetPorId() {

    }

    @Test
    void testGetTodos() {

    }
}
