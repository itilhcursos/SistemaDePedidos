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


    void setupFormaPagamento(){
    FormaPagamento formaPagamento = new FormaPagamento(BigInteger.ONE, "Forma Pagamento teste", true);
    formaPagamentoRepository.save(formaPagamento);
}
    @Test
    void testAlterarFormaPagamento() {

    }

    @Test
    void testBuscarFormaPagamentoPorId() {

    }

    @Test
    @DisplayName( " teste do Criar forma-pagamento")
    void testCriarFormaPagamento() throws Exception {
    setupFormaPagamento();
    FormaPagamentoDTO dto = new FormaPagamentoDTO(null,"forma-pagamento teste",true );
    FormaPagamentoDTO dtoRetorno =formaPagamentoService.criarFormaPagamento(dto);
    assertEquals(true,dtoRetorno.getId() != null);
    
    }
    @Test
    @DisplayName( " teste do Criar forma-pagamento duplicado")
    void testCriarFormaPagamentoDuplicado() throws Exception {
        setupFormaPagamento();
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null,"forma-pagamento teste",true );
        FormaPagamentoDTO dtoRetorno =formaPagamentoService.criarFormaPagamento(dto);
    
        assertThrows(FormaPagamentoDuplicadoException.class,()-> formaPagamentoService.criarFormaPagamento(dto));
    }
    @Test
    void testExcluirFormaPagamento() {

    }

    @Test
    void testListarFormasPagamento() {

    }
}
