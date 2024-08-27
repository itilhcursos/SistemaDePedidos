package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

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
    void testListarFormasPagamento() {

    }

    @Test
    void testBuscarFormaPagamentoPorId() {

    }

    @Test
    @DisplayName("teste de regra de Negocio - criar forma de pagamento")
    void testCriarFormaPagamento() throws Exception {
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null, "Forma de Pagamento teste", true);

        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);

        assertEquals(true, dtoRetorno.getId() != null);
    }


    //perguntar ao professor sobre se a validação - se não seria por descrição e o id que iria dizer se é duplicado
    @Test
    @DisplayName("teste de Regra de Negocio - forma de pagamento duplicado")
    void testCriarFormaPagamentoDuplicado() throws Exception {
        FormaPagamentoDTO dto = new FormaPagamentoDTO( null, "Forma de Pagamento teste duplicado", true);

        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);

        assertThrows(FormaPagamentoDuplicadoException.class, () -> formaPagamentoService.criarFormaPagamento(dto));
    }

    @Test
    @DisplayName("teste de alteração de nome")
    void testCriarFormaPagamentoDuplicadoAlterar() throws Exception {
        FormaPagamentoDTO dtoDescricaoErrada = new FormaPagamentoDTO(null, "Descrição errada", true);

        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dtoDescricaoErrada);

        FormaPagamentoDTO dtoDescricaoCorrigida= new FormaPagamentoDTO(dtoRetorno.getId(), "Descrição corrigida ", true);
        dtoDescricaoCorrigida = formaPagamentoService.alterarFormaPagamento(dtoRetorno.getId(), dtoDescricaoCorrigida);
        assertEquals(true, dtoDescricaoCorrigida.getId().equals(dtoDescricaoErrada.getId()));
    }
    @Test
    void testAlterarFormaPagamento() {

        
    }

    @Test
    void testExcluirFormaPagamento() {

    }

}

