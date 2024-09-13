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
import br.com.itilh.bdpedidos.sistemapedidos.exception.FormaPagamentoDuplicadaException;
import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;

@SpringBootTest
@ActiveProfiles("test")
public class FormaPagamentoServiceTest {

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    FormaPagamentoService formaPagamentoService;

    void setUpFormaPagamento() {
        FormaPagamento formaPagamento = new FormaPagamento(BigInteger.ONE, "FormaPagamento teste", true);
        formaPagamentoRepository.save(formaPagamento);
    }

    @Test
    void testCriarFormaPagamento() throws Exception {
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null,"Forma de Pagamento",true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);
        assertEquals(true, dtoRetorno.getId() != null);
    }

    @Test
    @DisplayName("Teste de regra de negócio -  FormaPagamento Duplicada")
    void testCriarFormaPagamentoDuplicada() throws Exception {
        setUpFormaPagamento();
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null, "Forma de Pagamento Duplicada", true);
        /* FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto); */
        assertThrows(FormaPagamentoDuplicadaException.class, () -> formaPagamentoService.criarFormaPagamento(dto));
    }

    //faltou os testes dos outros métodos da service
    
}