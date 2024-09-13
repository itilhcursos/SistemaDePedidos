package br.com.itilh.bdpedidos.sistemapedidos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.dto.FormaPagamentoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.dto.ProdutoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.FormaPagamentoDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;


    @SpringBootTest
    @ActiveProfiles("test")
public class FormaPagamentoServiceTest {

    @Autowired
    FormaPagamentoService formaPagamentoService;

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    void setupFormaPagamento(){
        FormaPagamento formaPagamento = new FormaPagamento(BigInteger.ONE, "forma pagamento teste",true);
        formaPagamentoRepository.save(formaPagamento);
    }

    @Test
    @DisplayName("Teste de criar forma de pagamento")
    void testCriarFormaPagamento() throws Exception {
        setupFormaPagamento();
        FormaPagamentoDTO dto = new FormaPagamentoDTO(BigInteger.ONE, "forma pagamento teste",true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);       
         assertEquals(true, dtoRetorno.getId() != null);
    }



    @Test
    @DisplayName("Teste de regra de negócio -  formaPagamento Duplicada")
    void testCriarFormaPagamentoDuplicado() throws Exception {
        setupFormaPagamento();
        FormaPagamentoDTO dto = new FormaPagamentoDTO(null,"Produto teste duplicado",true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dto);
        
        assertThrows(FormaPagamentoDuplicadoException.class, ()-> formaPagamentoService.criarFormaPagamento(dto));
    }

    @Test
    @DisplayName("Teste de alteração forma de pagemento")
    void testCriarFormaPagamentoAlterar() throws Exception {
        setupFormaPagamento();
        FormaPagamentoDTO dtoNomeErrado = new FormaPagamentoDTO(null,"Nome errado",true);
        FormaPagamentoDTO dtoRetorno = formaPagamentoService.criarFormaPagamento(dtoNomeErrado);
        
        FormaPagamentoDTO dtoNomeCorrigido = new FormaPagamentoDTO(dtoRetorno.getId(),"Nome corrigido",true);
        //tem um erro na linha abaixo
        dtoNomeCorrigido = formaPagamentoService.alterarFormaPagamento(dtoNomeCorrigido);
        assertEquals(true, dtoNomeCorrigido.getId().equals(dtoRetorno.getId()));
    }

    // esse teste não é de alteração
    @Test
    @DisplayName("Teste de alteração de nome duplicado")
    void testCriarFormaPagamentoDuplicadoAlterar() throws Exception {
        setupFormaPagamento();
        // criar FormaPagamento errado
        FormaPagamentoDTO dtoNomeErrado = new FormaPagamentoDTO(null,"Nome errado",true);
        dtoNomeErrado = formaPagamentoService.criarFormaPagamento(dtoNomeErrado);
        //criar FormaPagamento correto
        FormaPagamentoDTO dtoCorrigido = new FormaPagamentoDTO(null,"Nome corrigido",true);
        dtoCorrigido = formaPagamentoService.criarFormaPagamento(dtoCorrigido);

        // gerar erro ao tentar mudar o nome de forma de Pagamento para outra já existente
        FormaPagamentoDTO dtoNomeCorrigido = new FormaPagamentoDTO(dtoNomeErrado.getId(),"Nome corrigido",true);
        assertThrows(FormaPagamentoDuplicadoException.class, ()-> formaPagamentoService.alterarFormaPagamento(dtoNomeCorrigido.getId(), dtoNomeCorrigido));
    }
    

























}