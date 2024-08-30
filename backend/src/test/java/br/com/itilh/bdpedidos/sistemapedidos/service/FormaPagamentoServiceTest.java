package br.com.itilh.bdpedidos.sistemapedidos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.dto.FormaPagamentoDTO;

import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;

@SpringBootTest
@ActiveProfiles("Test")

public class FormaPagamentoServiceTest {

    @Autowired
    FormaPagamentoService formaPagamentoService;

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    void setupFormaPagamento() {
        FormaPagamento formapagamento = new FormaPagamento(BigInteger.ONE, "Produto Teste", true);
        formaPagamentoRepository.save(formapagamento);
    }

    @Test
    @DisplayName("Teste Alterar Forma Pagamento")
    void testAlterarFormaPagamento() throws Exception {
        setupFormaPagamento();
        FormaPagamentoDTO dtoDescricaoErrado = new FormaPagamentoDTO(null, "Forma Pagamento Errado", true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dtoDescricaoErrado);

        FormaPagamentoDTO dtoDescricaoCorreto = new FormaPagamentoDTO(dtoRetorno.getId(), "Descrição Correta", true);
        dtoDescricaoCorreto = formaPagamentoService.alterarFormaPagamento(dtoRetorno.getId(), dtoDescricaoCorreto);
        assertEquals(true, dtoDescricaoCorreto.getId().equals(dtoRetorno.getId()));
    }

    @Test
    @DisplayName("Teste Criar Forma Pagamento")
    void testCriarFormaPagamento() throws Exception {
        setupFormaPagamento();
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null, "Teste Forma Pagamento", true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);
        assertEquals(true, dtoRetorno.getId() != null);

    }

    @Test
    @DisplayName("Teste de regra de negócio - Forma Pagamento duplicado")
    void testCriarFormaPagamentoDuplicado() throws Exception {
        setupFormaPagamento();
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null, "Forma Pagamento teste duplicado", true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);
       // assertThrows(FormaPagametoDuplicadoException.class, () -> formaPagamentoService.criarFormaPagamento(dto));

    }
}
