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
    FormaPagamentoService formaPagamentoService;

    @Test
    void testCriarFormaPagamento() throws Exception {
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null,"teste nova forma de pagamento",true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);
        assertEquals(true, dtoRetorno.getId() != null);
    }

    @Test
    @DisplayName("Forma de Pagamento Duplicada")
    void testCriarFormaPagamentoDuplicada() throws Exception {
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null,"Forma de Pagamento teste duplicado", true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);
        
        assertThrows(FormaPagamentoDuplicadoException.class, ()-> formaPagamentoService.criarFormaPagamento(dto));
    }


    @Test
    @DisplayName("Teste de alteração de nome")
    void testCriarFormaPagamentoAlterar() throws Exception {
        FormaPagamentoDTO dtoNomeErrado = new FormaPagamentoDTO(null,"Nome errado2", true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dtoNomeErrado);

        FormaPagamentoDTO dtoNomeCorrigido = new FormaPagamentoDTO(dtoRetorno.getId(),"Nome corrigido", true);
        dtoNomeCorrigido = formaPagamentoService.alterarFormaPagamento(dtoRetorno.getId(), dtoNomeCorrigido);
        assertEquals(true, dtoNomeCorrigido.getId().equals(dtoRetorno.getId()));
    }

    @Test
    @DisplayName("Teste de alteração de nome duplicado")
    void testCriarFormaPagamentoDuplicadoAlterar() throws Exception {
        // criar produto errado
        FormaPagamentoDTO dtoNomeErrado = new FormaPagamentoDTO(null,"Nome errado", true);
        dtoNomeErrado = formaPagamentoService.criarFormaPagamento(dtoNomeErrado);
        //criar produto correto
        FormaPagamentoDTO dtoCorrigido = new FormaPagamentoDTO(null,"Nome corrigido", true);
        dtoCorrigido = formaPagamentoService.criarFormaPagamento(dtoCorrigido);

        // gerar erro ao tentar mudar o nome de um produto para outro já existente
        FormaPagamentoDTO dtoNomeCorrigido = new FormaPagamentoDTO(dtoNomeErrado.getId(),"Nome corrigido", true);
        assertThrows(FormaPagamentoDuplicadoException.class, ()-> formaPagamentoService.alterarFormaPagamento(dtoNomeCorrigido.getId(), dtoNomeCorrigido));
    }
}
