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
FormaPagamentoRepository formaPagamentoRepository;

@Autowired
FormaPagamentoService formaPagamentoService;


void setupFormaPagamento() {
        FormaPagamento formaPagamento = new FormaPagamento(BigInteger.ONE,"Forma Pagamento",true);
        formaPagamentoRepository.save(formaPagamento);
    }

@Test
    void testCriarFormaPagamento() throws Exception {
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null,"Forma Pagamento",true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);
        assertEquals(true, dtoRetorno.getId() != null);
    }

 @Test
    @DisplayName("Teste de regra de negócio -  Forma de Pagamento Duplicada")
    void testCriarFormaPagamentoDuplicada() throws Exception {
        setupFormaPagamento();
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null,"Forma Pagamento duplicada",true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);
        
        assertThrows(FormaPagamentoDuplicadoException.class, ()-> formaPagamentoService.criarFormaPagamento(dto));
    }


    @Test
    @DisplayName("Teste de alteração de forma de pagamento")
    void testAlterarFormaPagamento() throws Exception {
        setupFormaPagamento();
        FormaPagamentoDTO dtoCriado = new FormaPagamentoDTO(null, "Forma Pagamento Teste", true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dtoCriado);

        FormaPagamentoDTO dtoAlterado = new FormaPagamentoDTO(dtoRetorno.getId(), "Forma Pagamento Alterada", true);
        FormaPagamentoDTO dtoAlteradoRetorno = formaPagamentoService.alterarFormaPagamento(dtoRetorno.getId(), dtoAlterado);

        assertEquals("Forma Pagamento Alterada", dtoAlteradoRetorno.getDescricao());
    }

    @Test
    @DisplayName("Teste de alteração de forma de pagamento duplicada")
    void testAlterarFormaPagamentoDuplicada() throws Exception {
        setupFormaPagamento();
        FormaPagamentoDTO dtoCriado = new FormaPagamentoDTO(null, "Forma Pagamento Original", true);
        FormaPagamentoDTO dtoCriado2 = new FormaPagamentoDTO(null, "Forma Pagamento Duplicada", true);
        formaPagamentoService.criarFormaPagamento(dtoCriado);
        formaPagamentoService.criarFormaPagamento(dtoCriado2);

        dtoCriado.setDescricao("Forma Pagamento Duplicada");

        assertThrows(FormaPagamentoDuplicadoException.class, () -> formaPagamentoService.alterarFormaPagamento(dtoCriado.getId(), dtoCriado));
    }

    @Test
    @DisplayName("Teste de exclusão de forma de pagamento")
    void testExcluirFormaPagamento() throws Exception {
        setupFormaPagamento();
        FormaPagamentoDTO dtoCriado = new FormaPagamentoDTO(null, "Forma Pagamento a Ser Excluída", true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dtoCriado);

        formaPagamentoService.excluirFormaPagamento(dtoRetorno.getId());

        assertThrows(Exception.class, () -> formaPagamentoService.buscarFormaPagamentoPorId(dtoRetorno.getId()));
    }

}
