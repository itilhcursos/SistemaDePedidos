package br.com.itilh.bdpedidos.sistemapedidos.service;

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
    @DisplayName("Forma de Pagamento Duplicada" )
    void testCriarFormaPagamentoDuplicada() throws Exception {
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null,"Forma de Pagamento teste duplicado", true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);
        assertThrows(FormaPagamentoDuplicadoException.class, ()-> formaPagamentoService.criarFormaPagamento(dto));
    }

    
    @Test
    @DisplayName("Teste de alteração de nome duplicado" )
    void testCriarFormaPagamentoDuplicadoAlterar() throws Exception {
        FormaPagamentoDTO dtoNomeErrado = new FormaPagamentoDTO(null,"Nome errado",true);
        dtoNomeErrado = formaPagamentoService.criarFormaPagamento(dtoNomeErrado);

        FormaPagamentoDTO dtoCorrigido = new FormaPagamentoDTO(null,"Nome corrigido",true);
        dtoCorrigido = formaPagamentoService.criarFormaPagamento(dtoCorrigido);

        FormaPagamentoDTO dtoNomeCorrigido = new FormaPagamentoDTO(dtoNomeErrado.getId(),"Nome corrigido", true);
        assertThrows(FormaPagamentoDuplicadoException.class, ()-> formaPagamentoService.alterarFormaPagamento(dtoNomeCorrigido.getId(),dtoNomeCorrigido));
    }

}

