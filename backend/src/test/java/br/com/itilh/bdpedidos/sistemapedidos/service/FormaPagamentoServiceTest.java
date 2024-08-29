package br.com.itilh.bdpedidos.sistemapedidos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void testCriarFormaPagamento() throws Exception{
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null, "Forma Pagamento teste", true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);
        assertEquals(true, dtoRetorno.getId() != null); 
    }

    @Test
    @DisplayName("Teste de regra de negócio -  Forma Pagamento Duplicado")
    void testCriarFormaPagamentoDuplicado() throws Exception {
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null, "Forma Pagamento teste duplicado", true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);
        assertThrows(FormaPagamentoDuplicadoException.class, ()-> formaPagamentoService.criarFormaPagamento(dto));
    }

    @Test
    @DisplayName("Teste de alteração de descrição")
    void testCriarFormaPagamentoAlterar() throws Exception {
        FormaPagamentoDTO dtoDescricaoErrada = new FormaPagamentoDTO(null,"Descrição errada",true);

        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dtoDescricaoErrada);

        FormaPagamentoDTO dtoDescricaoCorrigida = new FormaPagamentoDTO(dtoRetorno.getId(),"Descrição corrigida",true);
        dtoDescricaoCorrigida = formaPagamentoService.alterarFormaPagamento(dtoRetorno.getId(), dtoDescricaoCorrigida);
        assertEquals(true, dtoDescricaoCorrigida.getId().equals(dtoRetorno.getId()));
    }

    @Test
    @DisplayName("Teste de alteração de descrição duplicada")
    void testCriarFormaPagamentoDuplicadoAlterar() throws Exception {
        FormaPagamentoDTO dtoDescricaoErrada = new FormaPagamentoDTO(null,"Descrição errada",true);
        dtoDescricaoErrada = formaPagamentoService.criarFormaPagamento(dtoDescricaoErrada);
    
        FormaPagamentoDTO dtoCorrigido = new FormaPagamentoDTO(null,"Descrição corrigida",true);
        dtoCorrigido = formaPagamentoService.criarFormaPagamento(dtoCorrigido);

        FormaPagamentoDTO dtoDescricaoCorrigida = new FormaPagamentoDTO(dtoDescricaoErrada.getId(),"Descrição corrigida",true);
        assertThrows(FormaPagamentoDuplicadoException.class, ()-> formaPagamentoService.alterarFormaPagamento(dtoDescricaoCorrigida.getId(), dtoDescricaoCorrigida));
    }
}
