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
    FormaPagamentoService formaPagamentoService;

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    void setUpFormaPagamento() {
        FormaPagamento formaPagamento = new FormaPagamento(
                BigInteger.ONE,
                "Forma Pagamento teste",
                true);

    }

    @Test
    void testCriarFormaPagamento() throws Exception {
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null, "forma Pagamento de teste", true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);
        assertEquals(true, dtoRetorno.getId() != null);
    }

    @Test
    @DisplayName("Test de crição de uma forma de pagamento")
    void testAlterarFormaPagamento() throws Exception {
        FormaPagamentoDTO testeDTO = new FormaPagamentoDTO(null, "formaPagamento de teste", null);
        FormaPagamentoDTO retorna = formaPagamentoService.criarFormaPagamento(testeDTO);
        assertEquals(true, retorna.getId() != null);

    }

    @Test
    @DisplayName("Test de crição de uma forma de pagamento duplicada")
    void testFormaPagamentoDuplicada() throws Exception {
        FormaPagamentoDTO testeDTO = new FormaPagamentoDTO(null, "formaPagamento duplicada de teste", null);
        FormaPagamentoDTO retorno = formaPagamentoService.criarFormaPagamento(testeDTO);
        assertThrows(FormaPagamentoDuplicadoException.class, () -> formaPagamentoService.criarFormaPagamento(testeDTO));

    }

    @Test
    @DisplayName("Teste de alteração de nome duplicado")
    void testCriarFormaPagamentoDuplicadoAlterar() throws Exception {
        setUpFormaPagamento();
        // criar forma pagamento errado
        FormaPagamentoDTO dtoNomeErrado = new FormaPagamentoDTO(null, "nome errado", null);
        dtoNomeErrado = formaPagamentoService.criarFormaPagamento(dtoNomeErrado);

        // criar forma pagamento correto
        FormaPagamentoDTO dtoCorrigido = new FormaPagamentoDTO(null, "nome corrigido", null);
        dtoCorrigido = formaPagamentoService.criarFormaPagamento(dtoCorrigido);

        // gerar erro ao tentar mudar o nome de uma forma pagamento para outro já
        // existente
        FormaPagamentoDTO dtoNomeCorrigido = new FormaPagamentoDTO(null, "nome corrigido", null);
        assertThrows(FormaPagamentoDuplicadoException.class,
                () -> formaPagamentoService.alterarFormaPagamento(dtoNomeCorrigido.getId(), dtoNomeCorrigido));
    }

}
