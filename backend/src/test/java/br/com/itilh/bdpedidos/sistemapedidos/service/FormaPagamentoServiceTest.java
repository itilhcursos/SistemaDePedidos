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
import br.com.itilh.bdpedidos.sistemapedidos.dto.MunicipioDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.FormaPagamentoDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.MunicipioDuplicadoException;

@SpringBootTest
@ActiveProfiles("test")
public class FormaPagamentoServiceTest {

    @Autowired
    FormaPagamentoService formaPagamentoService;

    @Test
    void testCriarFormaPagamento() throws Exception{
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null, "Test", true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);

        assertEquals(true, dtoRetorno.getId() != null);
        //  assertEquals(true, dtoRetorno.getId() != null);
        // assertThrows(MunicipioDuplicadoException.class, ()-> municipioService.criarMunicipio(dto));
    }

    @Test
    @DisplayName("Teste de alteração de nome")
    void testCriarMunicipioAlterar() throws Exception {
        FormaPagamentoDTO dtoNomeErrado = new FormaPagamentoDTO(null, "Test", true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dtoNomeErrado);

        FormaPagamentoDTO dtoNomeCorrigido = new FormaPagamentoDTO(dtoRetorno.getId(), "Test 01", true);
        dtoNomeCorrigido = formaPagamentoService.alterarFormaPagamento(dtoRetorno.getId(), dtoNomeCorrigido);

        assertEquals(true, dtoNomeCorrigido.getId().equals(dtoRetorno.getId()));
    }

    @Test
    void testCriarFormaPagamentoDuplicado() throws Exception{
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null, "Test", true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);

        assertThrows(FormaPagamentoDuplicadoException.class, ()-> formaPagamentoService.criarFormaPagamento(dto));

    }

    @Test
    @DisplayName("Teste de alteração de nome duplicado")
    void testCriarFormaPagamentoDuplicadoAlterar() throws Exception {
        // criar municipio errado
        FormaPagamentoDTO dtoNomeErrado = new FormaPagamentoDTO(null, "Test", true);
        dtoNomeErrado = formaPagamentoService.criarFormaPagamento(dtoNomeErrado);
        //criar municicipio correto
        FormaPagamentoDTO dtoCorrigido = new FormaPagamentoDTO(null, "Test 01", true);
        dtoCorrigido = formaPagamentoService.criarFormaPagamento(dtoCorrigido);

        // gerar erro ao tentar mudar o nome de um municipio para outro já existente
        FormaPagamentoDTO dtoNomeCorrigido = new FormaPagamentoDTO(dtoNomeErrado.getId(),"Test", true);
        assertThrows(FormaPagamentoDuplicadoException.class, ()-> formaPagamentoService.alterarFormaPagamento(dtoNomeCorrigido.getId(), dtoNomeCorrigido));
    }

}

