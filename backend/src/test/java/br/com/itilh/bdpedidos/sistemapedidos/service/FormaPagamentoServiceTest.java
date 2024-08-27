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
    @DisplayName("teste de alterar produto")
    void testAlterarFormaPagamento() throws Exception {
        setupFormaPagamento();
    FormaPagamentoDTO dtoDescricaoErrado = new FormaPagamentoDTO(BigInteger.ONE,"forma-pagamento errado", true);
    FormaPagamentoDTO dtoRetorno =formaPagamentoService.criarFormaPagamento(dtoDescricaoErrado);
    FormaPagamentoDTO dtoDescricaoCorrigido = new FormaPagamentoDTO(dtoRetorno.getId(),"forma-pagamento corrigido", true);
   dtoDescricaoCorrigido = formaPagamentoService.alterarFormaPagamento(dtoRetorno.getId(), dtoDescricaoCorrigido);
    assertEquals(true,dtoDescricaoCorrigido.getId().equals(dtoRetorno.getId()));
}

@Test
@DisplayName("teste de produto duplicado por alteração")
void testAlterarFormaPagamentoDuplicado() throws Exception {
    setupFormaPagamento();
        FormaPagamentoDTO dtoDescricaoErrado = new FormaPagamentoDTO(BigInteger.ONE,"forma-pagamento errado", true);
         dtoDescricaoErrado =formaPagamentoService.criarFormaPagamento(dtoDescricaoErrado);
    
        FormaPagamentoDTO dtoDescricaoCorrigido = new FormaPagamentoDTO(null,"forma-pagamento corrigido", true);
        dtoDescricaoCorrigido = formaPagamentoService.criarFormaPagamento(dtoDescricaoCorrigido);
   
        FormaPagamentoDTO dtoCorrigido = new FormaPagamentoDTO(dtoDescricaoErrado.getId(),"corrigido", true);
   
        assertThrows(FormaPagamentoDuplicadoException.class,()-> formaPagamentoService.alterarFormaPagamento(dtoCorrigido.getId(), dtoCorrigido));




}

    @Test
    void testBuscarFormaPagamentoPorId() {

    }

    @Test
    @DisplayName( " teste do Criar forma-pagamento")
    void testCriarFormaPagamento() throws Exception {
    setupFormaPagamento();
    FormaPagamentoDTO dto = new FormaPagamentoDTO(BigInteger.ONE,"Forma Pagamento teste",true );
    FormaPagamentoDTO dtoRetorno =formaPagamentoService.criarFormaPagamento(dto);
    assertEquals(true,dtoRetorno.getId() != null);
    
    }
    @Test
    @DisplayName( " teste do Criar forma-pagamento duplicado")
    void testCriarFormaPagamentoDuplicado() throws Exception {
        setupFormaPagamento();
        FormaPagamentoDTO dto = new FormaPagamentoDTO(BigInteger.ONE,"Forma Pagamento teste",true );
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
