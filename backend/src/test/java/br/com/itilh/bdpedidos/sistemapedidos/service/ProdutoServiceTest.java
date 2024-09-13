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
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;


    @SpringBootTest
    @ActiveProfiles("test")
public class ProdutoServiceTest {

    @Autowired
    ProdutoService produtoService;

    @Autowired
    ProdutoRepository produtoRepository;

    void setupProduto(){
        Produto produto = new Produto(BigInteger.ONE, "produto teste",(double)10,BigDecimal.valueOf(2.99),true);
       produtoRepository.save(produto);
    }

    @Test
    @DisplayName("Teste de criar produto")
    void testCriarProduto() throws Exception {
        setupProduto();
        ProdutoDTO dto = new ProdutoDTO(null,"Produto teste",(double)1,BigDecimal.valueOf(2.99),true);
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dto);        assertEquals(true, dtoRetorno.getId() != null);
    }



    @Test
    @DisplayName("Teste de regra de negócio -  produto Duplicado")
    void testCriarProdutoDuplicado() throws Exception {
        setupProduto();
        ProdutoDTO dto = new ProdutoDTO(null,"Produto teste duplicado",(double)1,BigDecimal.valueOf(2.99),true);
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dto);
        
        assertThrows(ProdutoDuplicadoException.class, ()-> produtoService.criarProduto(dto));
    }

    @Test
    @DisplayName("Teste de alteração de nome")
    void testCriarProdutoAlterar() throws Exception {
        setupProduto();
        ProdutoDTO dtoNomeErrado = new ProdutoDTO(null,"Nome errado",(double)1,BigDecimal.valueOf(0.00),true);
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dtoNomeErrado);
        
        ProdutoDTO dtoNomeCorrigido = new ProdutoDTO(dtoRetorno.getId(),"Nome corrigido",(double)1,BigDecimal.valueOf(0.00),true);
        dtoNomeCorrigido = produtoService.alterarProduto(dtoNomeCorrigido);
        assertEquals(true, dtoNomeCorrigido.getId().equals(dtoRetorno.getId()));
    }

    @Test
    @DisplayName("Teste de alteração de nome duplicado")
    void testCriarProdutoDuplicadoAlterar() throws Exception {
        setupProduto();
        // criar produto errado
        ProdutoDTO dtoNomeErrado = new ProdutoDTO(null,"Nome errado",(double)1,BigDecimal.valueOf(0.00),true);
        dtoNomeErrado = produtoService.criarProduto(dtoNomeErrado);
        //criar produto correto
        ProdutoDTO dtoCorrigido = new ProdutoDTO(null,"Nome corrigido",(double)1,BigDecimal.valueOf(0.00),true);
        dtoCorrigido = produtoService.criarProduto(dtoCorrigido);

        // gerar erro ao tentar mudar o nome de um produto para outro já existente
        ProdutoDTO dtoNomeCorrigido = new ProdutoDTO(dtoNomeErrado.getId(),"Nome corrigido",1.0, BigDecimal.valueOf(10), true);
        assertThrows(ProdutoDuplicadoException.class, ()-> produtoService.
    //linha com erro
        alterarProduto(dtoNomeCorrigido.getId(), dtoNomeCorrigido));
    }
    

























}