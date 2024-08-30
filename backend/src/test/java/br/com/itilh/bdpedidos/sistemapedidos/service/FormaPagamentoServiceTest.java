package br.com.itilh.bdpedidos.sistemapedidos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.com.itilh.bdpedidos.sistemapedidos.dto.FormaPagamentoDTO;

import br.com.itilh.bdpedidos.sistemapedidos.exception.FormaPagamentoException;


public class FormaPagamentoServiceTest {
    
    
    
    FormaPagamentoService formaPagamentoService;


    @Test
    void testAlterarFormaPagamento() {

    }
    @Test
    @DisplayName("Test de criação de um produto")
    void testCriarFormaPagamento() throws Exception {

        FormaPagamentoDTO dto = new FormaPagamentoDTO(null, "Pagamento teste",  true);
      FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);
      assertEquals(true, dtoRetorno.getId()!=null);

    }


    
  @Test
  @DisplayName("Teste de regra de negócio -  Forma de Pagamento Duplicada")
  void testCriarFormaPagamentoDuplicado() throws Exception {
    FormaPagamentoDTO dtoPagamento = new FormaPagamentoDTO(null, "Pagamento teste",  true);
    FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dtoPagamento);
      assertThrows(FormaPagamentoException.class, ()-> formaPagamentoService.criarFormaPagamento(dtoPagamento));
  }
}
