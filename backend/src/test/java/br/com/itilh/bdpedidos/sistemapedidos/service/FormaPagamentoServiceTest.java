package br.com.itilh.bdpedidos.sistemapedidos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void testCriarFormaPagamento() throws Exception{
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null, "Test", true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);

        assertEquals(true, dtoRetorno.getId() != null);
        //  assertEquals(true, dtoRetorno.getId() != null);
        // assertThrows(MunicipioDuplicadoException.class, ()-> municipioService.criarMunicipio(dto));
    }

    @Test
    void testCriarFormaPagamentoDuplicado() throws Exception{
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null, "Test", true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);

        assertThrows(FormaPagamentoDuplicadoException.class, ()-> formaPagamentoService.criarFormaPagamento(dto));

    }
}

