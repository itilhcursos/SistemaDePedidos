package br.com.itilh.bdpedidos.sistemapedidos.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.dto.FormaPagamentoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.FormaPagamentoDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;

@SpringBootTest
@ActiveProfiles("test")
public class FormaPagamentoServiceTest {

    @Autowired
    FormaPagamentoService formaPagamentoService;

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    @Test
    void testCriarFormaPagamento() throws Exception {
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null, "Forma de pagamento teste", true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);
        assertNotNull(dtoRetorno.getId());
    }

    @Test
    @DisplayName("Teste de regra de negócio - Forma de Pagamento Duplicada")
    void testCriarFormaPagamentoDuplicado() throws Exception {
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null, "Forma de pagamento duplicada", true);
        formaPagamentoService.criarFormaPagamento(dto);

        assertThrows(FormaPagamentoDuplicadoException.class,
                () -> formaPagamentoService.criarFormaPagamento(dto));
    }

    @Test
    @DisplayName("Teste de alteração de forma de pagamento")
    void testAlterarFormaPagamento() throws Exception {
        FormaPagamentoDTO dtoOriginal = new FormaPagamentoDTO(null, "Forma de pagamento original", true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dtoOriginal);

        FormaPagamentoDTO dtoAlterado = new FormaPagamentoDTO(dtoRetorno.getId(), "Forma de pagamento alterada", true);
        dtoAlterado = formaPagamentoService.alterarFormaPagamento(dtoRetorno.getId(), dtoAlterado);
        assertEquals("Forma de pagamento alterada", dtoAlterado.getDescricao());
    }

    @Test
    @DisplayName("Teste de exclusão de forma de pagamento")
    void testExcluirFormaPagamento() throws Exception {
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null, "Forma de pagamento a excluir", true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);

        String resultado = formaPagamentoService.excluirFormaPagamento(dtoRetorno.getId());
        assertEquals("Excluído", resultado);
    }

    @Test
    @DisplayName("Teste de busca por ID existente")
    void testBuscarFormaPagamentoPorIdExistente() throws Exception {
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null, "Forma de pagamento existente", true);
        FormaPagamentoDTO dtoCriado = formaPagamentoService.criarFormaPagamento(dto);

        FormaPagamentoDTO dtoBuscado = formaPagamentoService.buscarFormaPagamentoPorId(dtoCriado.getId());
        assertEquals(dtoCriado.getDescricao(), dtoBuscado.getDescricao());
    }

    @Test
    @DisplayName("Teste de busca por ID inexistente")
    void testBuscarFormaPagamentoPorIdInexistente() {
        assertThrows(IdInexistenteException.class,
                () -> formaPagamentoService.buscarFormaPagamentoPorId(BigInteger.valueOf(999999L)));
    }
}
