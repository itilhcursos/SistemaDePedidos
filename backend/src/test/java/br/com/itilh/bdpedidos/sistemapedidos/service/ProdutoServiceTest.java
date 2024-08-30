package br.com.itilh.bdpedidos.sistemapedidos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ProdutoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoEstoqueNegativoException;

@SpringBootTest
@ActiveProfiles("test")
public class ProdutoServiceTest {

  @Autowired
  ProdutoService produtoService;

  @Test
  @DisplayName("Test de criação de um produto")
  void testCriarProduto() throws Exception {
      ProdutoDTO dto = new ProdutoDTO(null, "produto de teste", 10.00, BigDecimal.valueOf(12.99), true);
      ProdutoDTO dtoRetorno = produtoService.criarProduto(dto);
      assertEquals(true, dtoRetorno.getId()!=null);
  }

  @Test
  @DisplayName("Teste de regra de negócio -  Produto Duplicado")
  void testCriarProdutoDuplicado() throws Exception {
      ProdutoDTO dto = new ProdutoDTO(null, "produto de teste duplicado", 10.00, BigDecimal.valueOf(12.99), true);
      ProdutoDTO dtoRetorno = produtoService.criarProduto(dto);
      assertThrows(ProdutoDuplicadoException.class, ()-> produtoService.criarProduto(dto));
  }

  @Test
  @DisplayName("Teste de alteração de nome")
  void testCriarProdutoAlterar() throws Exception {
      ProdutoDTO dtoNomeErrado = new ProdutoDTO(null, "Nome errado", 10.00, BigDecimal.valueOf(12.99), true);
      ProdutoDTO dtoRetorno = produtoService.criarProduto(dtoNomeErrado);
      
      ProdutoDTO dtoNomeCorrigido = new ProdutoDTO(dtoRetorno.getId(),"Nome corrigido", 10.00, BigDecimal.valueOf(12.99), true);
      dtoNomeCorrigido = produtoService.alterarProduto(dtoRetorno.getId(), dtoNomeCorrigido);
      assertEquals(true, dtoNomeCorrigido.getId().equals(dtoRetorno.getId()));
  }

  @Test
  @DisplayName("Teste de alteração de nome duplicado")
  void testCriarProdutoDuplicadoAlterar() throws Exception {
      // criar produto errado
      ProdutoDTO dtoNomeErrado = new ProdutoDTO(null, "Nome errado", 10.00, BigDecimal.valueOf(12.99), true);
      dtoNomeErrado = produtoService.criarProduto(dtoNomeErrado);
      //criar produto correto
      ProdutoDTO dtoCorrigido = new ProdutoDTO(null,"Nome corrigido", 10.00, BigDecimal.valueOf(12.99), true);
      dtoCorrigido = produtoService.criarProduto(dtoCorrigido);

      // gerar erro ao tentar mudar o nome de um produto para outro já existente
      ProdutoDTO dtoNomeCorrigido = new ProdutoDTO(dtoNomeErrado.getId(),"Nome errado", 10.00, BigDecimal.valueOf(12.99), true);
      assertThrows(ProdutoDuplicadoException.class, ()-> produtoService.alterarProduto(dtoNomeCorrigido.getId(), dtoNomeCorrigido));
  }




  @Test
  @DisplayName("Test de estoque negativo de produto")
  void testProdutoEstoqueNEgativo() throws Exception {
      
    ProdutoDTO dto = new ProdutoDTO(null, "produto de teste", Double.valueOf(0), BigDecimal.valueOf(12.99), true);
      ProdutoDTO dtoRetorno = produtoService.criarProduto(dto);


      assertThrows(ProdutoEstoqueNegativoException.class,()-> produtoService.criarProduto(dtoRetorno)) ;
  }


  @Test
  @DisplayName("Test de alteracao de estoque negativo de produto")
  void testProdutoEstoqueNEgativoAlterar() throws Exception {
      
    ProdutoDTO dtoNegativo = new ProdutoDTO(null, "produto de teste", Double.valueOf(0), BigDecimal.valueOf(12.99), true);
    dtoNegativo = produtoService.criarProduto(dtoNegativo);

    ProdutoDTO dtoPossitivo = new ProdutoDTO(null, "produto de teste", Double.valueOf(0), BigDecimal.valueOf(12.99), true);
    dtoPossitivo = produtoService.criarProduto(dtoPossitivo);

    ProdutoDTO dtoCorrigido = new ProdutoDTO(dtoNegativo.getId(),"Nome errado", 10.00, BigDecimal.valueOf(12.99), true);
    assertThrows(ProdutoEstoqueNegativoException.class, ()-> produtoService.alterarProduto(dtoCorrigido.getId(), dtoCorrigido));

      
  }


}
