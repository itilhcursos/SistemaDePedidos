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
import br.com.itilh.bdpedidos.sistemapedidos.dto.ProdutoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoEstoqueNegativoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoPrecoNegativoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;

@SpringBootTest
@ActiveProfiles("test") 
public class ProdutoServiceTest {

   

    @Autowired
    ProdutoService produtoService;

    @Autowired
    ProdutoRepository produtoRepository;    
    
   
     void setUpProduto() {
        Produto produto = new Produto(BigInteger.ONE, "produto teste", null, null, null);
        produtoRepository.save(produto);
    }
      //========================================================
      @Test
      @DisplayName("Test de criação de um produto")
      void testCriarProduto() throws Exception {
         ProdutoDTO testeDTO = new ProdutoDTO(null, "produto de teste", null, null, true);
         ProdutoDTO retorna = produtoService.criarProduto(testeDTO);
         assertEquals(true, retorna.getId() != null);
   
    }
    //==========================================================
    @Test
    @DisplayName("Test de criação de um produto duplicado")
    void testProdutoDuplicado() throws Exception {
       ProdutoDTO testeDTO = new ProdutoDTO(null, "produto de teste duplicado", (double)10, BigDecimal.valueOf(1000), true);
       ProdutoDTO retorna = produtoService.criarProduto(testeDTO);
       assertThrows(ProdutoDuplicadoException.class, ()-> produtoService.criarProduto(testeDTO));

 
    }
    //============================================================
   @Test
    @DisplayName("Teste de alteração de nome")
    void testCriarMunicipioAlterar() throws Exception {
        setUpProduto();
        ProdutoDTO dtoNomeErrado = new ProdutoDTO(null, "teste produto errado ", (double)10, BigDecimal.valueOf(1000), true);
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dtoNomeErrado);

        ProdutoDTO dtoNomeCorrigido = new ProdutoDTO(null, "teste produto Corrigido", (double)10, BigDecimal.valueOf(2000), true);
        dtoNomeCorrigido = produtoService.alterarProduto(dtoRetorno.getId(), dtoNomeCorrigido);
        assertEquals(false, dtoNomeCorrigido.getId().equals(dtoRetorno.getId()));
    }
   
    
    //========================================================
     @Test
    @DisplayName("Teste de alteração de nome duplicado")
    void testCriarProdutoDuplicadoAlterar() throws Exception {
        setUpProduto();
    // criar produto errado
    ProdutoDTO dtoNomeErrado = new ProdutoDTO(null, "teste nome produto Errado", (double)10, BigDecimal.valueOf(2000), true);
    dtoNomeErrado = produtoService.criarProduto(dtoNomeErrado);

    //criar produto correto
    ProdutoDTO dtoCorrigido = new ProdutoDTO(null, "teste nome produto Corrigido", (double)10, BigDecimal.valueOf(2000), true);
    dtoCorrigido = produtoService.criarProduto(dtoCorrigido);

    // gerar erro ao tentar mudar o nome de um produto para outro já existente
    ProdutoDTO dtoNomeCorrigido = new ProdutoDTO(dtoNomeErrado.getId(), "teste nome produto Corrigido", (double)10, BigDecimal.valueOf(2000), true);
    assertThrows(ProdutoDuplicadoException.class, ()-> produtoService.alterarProduto(dtoNomeCorrigido.getId(), dtoNomeCorrigido));
    }
    //=============================================================
    @Test
    @DisplayName("teste produto ao criar produto com estoque negativo")
    void testCriarProdutoEstoqueNegativo() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO(null, "Produto teste estoque negativo", -2.0, BigDecimal.valueOf(20), true);
        assertThrows(ProdutoEstoqueNegativoException.class, () -> produtoService.criarProduto(produtoDTO));
    }
    //========================================================================
    @Test
    @DisplayName("teste produto ao alterar produto para estoque negativo")
    void testAlterarProdutoEstoqueNegativo() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO(null, "Nome errado", 2.0, BigDecimal.valueOf(20), true);
        ProdutoDTO produtoCriado = produtoService.criarProduto(produtoDTO);

        produtoCriado.setQuantidadeEstoque(-1.0);
        assertThrows(ProdutoEstoqueNegativoException.class, () -> produtoService.alterarProduto(produtoCriado.getId(), produtoCriado));
    }
    //========================================================================
    @Test
    @DisplayName("teste produto ao criar produto com preço negativo")
    void testCriarProdutoPrecoNegativo() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO(null, "Produto teste preço negativo", 2.0, BigDecimal.valueOf(-20), true);
        assertThrows(ProdutoPrecoNegativoException.class, () -> produtoService.criarProduto(produtoDTO));
    }
    //========================================================================
    @Test
    @DisplayName(" teste produto ao alterar produto para preço negativo")
    void testAlterarProdutoPrecoNegativo() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO(null, "Nome errado", 2.0, BigDecimal.valueOf(20), true);
        ProdutoDTO produtoCriado = produtoService.criarProduto(produtoDTO);
        produtoCriado.setPreco(BigDecimal.valueOf(-20));
        assertThrows(ProdutoPrecoNegativoException.class, () -> produtoService.alterarProduto(produtoCriado.getId(), produtoCriado));
    }
}
   

