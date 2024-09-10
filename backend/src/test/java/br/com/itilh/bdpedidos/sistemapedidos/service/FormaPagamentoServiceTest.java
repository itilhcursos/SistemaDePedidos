package br.com.itilh.bdpedidos.sistemapedidos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


import java.math.BigInteger;
import org.springframework.data.domain.Page;
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
    @DisplayName("teste de buscar forma-pagamento por id")
    void testBuscarFormaPagamentoPorId()throws Exception {
        setupFormaPagamento();
        FormaPagamentoDTO formaPagamentoDTO = formaPagamentoService.buscarFormaPagamentoPorId(BigInteger.ONE);
        assertEquals(BigInteger.ONE, formaPagamentoDTO.getId());

    }

    @Test
    @DisplayName("teste deforma-pagamento  id nao encontrado")
    void testFormaPagamentoIdNaoEncontrado()throws Exception {
        setupFormaPagamento();
     
        FormaPagamentoDTO formaPagamentoDTO = formaPagamentoService.buscarFormaPagamentoPorId(BigInteger.valueOf(7897897412L));
         assertEquals(null, formaPagamentoDTO);
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
    @DisplayName("teste de excluir forma-pagamento")
    void testExcluirFormaPagamento() throws Exception{
        setupFormaPagamento();
    formaPagamentoService.excluirFormaPagamento(BigInteger.ONE);

    FormaPagamentoDTO formaPagamentoDTO = formaPagamentoService.buscarFormaPagamentoPorId(BigInteger.ONE);
    assertNull(formaPagamentoDTO);
    }

    @Test
    @DisplayName("teste de excluir forma-pagamento nao existente")
    void testExcluirFormaPagamentoNaoExistente() throws Exception{
        setupFormaPagamento();
        formaPagamentoService.excluirFormaPagamento(BigInteger.TEN);
FormaPagamentoDTO formaPagamentoDTO = formaPagamentoService.buscarFormaPagamentoPorId(BigInteger.ONE);
assertEquals(BigInteger.ONE, formaPagamentoDTO);
    }



    @Test
    @DisplayName(" teste de listar formas-pagamento")
    void testListarFormasPagamento() throws Exception{

        Page<FormaPagamentoDTO> formaPagamentoList =  formaPagamentoService.listarFormasPagamento(null);

         assertEquals(null, formaPagamentoList);

         FormaPagamentoDTO formaPagamento1 = null;

         for(FormaPagamentoDTO formaPagamento : formaPagamentoList){
         if(formaPagamento.getId().equals(BigInteger.ONE)){
             formaPagamento1 = formaPagamento;}
         }

assertNull(formaPagamento1);
assertEquals(BigInteger.ONE, formaPagamento1.getAtivo());
assertEquals("Forma Pagamento teste", formaPagamento1.getDescricao());
assertEquals(true, formaPagamento1.getAtivo());

    }
}