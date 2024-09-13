package br.com.itilh.bdpedidos.sistemapedidos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.dto.FormaPagamentoDTO;

import br.com.itilh.bdpedidos.sistemapedidos.exception.FormaPagamentoDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;

import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;

@SpringBootTest
@ActiveProfiles("test")
public class FormaPagamentoServiceTest {
    @Autowired
    FormaPagamentoRepository FormaPagamentoRepository;

    @Autowired
    FormaPagamentoService formaPagamentoService;

    void setupFormaPagamento() {
        FormaPagamento formaPagamento = new FormaPagamento(BigInteger.ONE, "Forma Pagamento", true);
        FormaPagamentoRepository.save(formaPagamento);
    }

    @Test
    void testCriarFormaPagamento() throws Exception {
        setupFormaPagamento();
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null, "Forma Pagamento", true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);
        assertEquals(true, dtoRetorno.getId() != null);
    }

    @Test
    @DisplayName("Teste de regra - Forma de Pagamento Duplicada")
    void testCriarFormaPagamentoDuplicada() throws Exception {
        setupFormaPagamento();
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null, "Forma Pagamento duplicada,true", null);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);

        assertThrows(FormaPagamentoDuplicadoException.class, () -> formaPagamentoService.criarFormaPagamento(dto));
    }

    //faltaram os teste dos demais métodos da service

}
