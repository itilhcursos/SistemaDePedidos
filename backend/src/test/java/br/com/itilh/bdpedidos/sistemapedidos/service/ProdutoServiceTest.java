package br.com.itilh.bdpedidos.sistemapedidos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ProdutoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
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

  void setUpProduto(){
    Produto produto = new Produto(BigInteger.ONE, "produto teste",Double.valueOf(10.00), BigDecimal.valueOf(12.99), true);
    produtoRepository.save(produto);
  }

  @Test
  @DisplayName("Teste de busca por ID existente")
  void testBuscarProdutoPorIdExistente() throws Exception {
    setUpProduto();
    ProdutoDTO dto = new ProdutoDTO(BigInteger.ONE, "produto teste", Double.valueOf(10.00), BigDecimal.valueOf(12.99), true);
    ProdutoDTO dtoBuscado = produtoService.buscarProdutoPorId(BigInteger.ONE);
    assertEquals(dto.getDescricao(), dtoBuscado.getDescricao());
  }

  @Test
  @DisplayName("Teste de busca por ID inexistente")
  void testBuscarProdutoPorIdInexistente() {
    setUpProduto();
    ProdutoDTO dto = new ProdutoDTO(BigInteger.TWO, "produto teste inexistente", Double.valueOf(10.00), BigDecimal.valueOf(12.99), true);
    assertThrows(IdInexistenteException.class, () -> produtoService.buscarProdutoPorId(dto.getId()));
  }

  @Test
  @DisplayName("Test de criação de um produto")
  void testCriarProduto() throws Exception {
    ProdutoDTO dto = new ProdutoDTO(null, "produto de teste", Double.valueOf(10.00), BigDecimal.valueOf(12.99), true);
    ProdutoDTO dtoRetorno = produtoService.criarProduto(dto);
    assertEquals(true, dtoRetorno.getId() != null);
  }

  @Test
  @DisplayName("Teste de criação de produto duplicado")
  void testCriarProdutoDuplicado() throws Exception {
    setUpProduto();
    ProdutoDTO dto = new ProdutoDTO(BigInteger.ONE, "produto teste", Double.valueOf(10.00), BigDecimal.valueOf(12.99), true);
    assertThrows(ProdutoDuplicadoException.class, () -> produtoService.criarProduto(dto));
  }

  @Test
  @DisplayName("Teste de criação de produto com o estoque negativo")
  void testCriarProdutoEstoqueNegativo() {
    setUpProduto();
    ProdutoDTO dto = new ProdutoDTO(null, "Produto com estoque negativo", Double.valueOf(-10.00), BigDecimal.valueOf(12.99), true);
    assertThrows(ProdutoEstoqueNegativoException.class, () -> produtoService.criarProduto(dto));
  }

  @Test
  @DisplayName("Teste de criação de produto com preço negativo")
  void testCriarProdutoPrecoNegativo() {
    ProdutoDTO dto = new ProdutoDTO(null, "Produto com preço negativo", Double.valueOf(10.00), BigDecimal.valueOf(-12.99), true);
    assertThrows(ProdutoPrecoNegativoException.class, () -> produtoService.criarProduto(dto));
  }

  @Test
  @DisplayName("Teste de alteração do produto")
  void testAlterarProduto() throws Exception {
      ProdutoDTO dtoNomeErrado = new ProdutoDTO(null, "Nome errado", 10.00, BigDecimal.valueOf(12.99), true);
      ProdutoDTO dtoRetorno = produtoService.criarProduto(dtoNomeErrado);
      ProdutoDTO dtoNomeCorrigido = new ProdutoDTO(dtoRetorno.getId(), "Nome corrigido", 10.00, BigDecimal.valueOf(12.99), true);
      dtoNomeCorrigido = produtoService.alterarProduto(dtoRetorno.getId(), dtoNomeCorrigido);
      assertEquals(true, dtoNomeCorrigido.getId().equals(dtoRetorno.getId()));
  }

  @Test
  @DisplayName("Teste de exclusão de produto")
  void testExcluirProduto() throws Exception {
      ProdutoDTO dto = new ProdutoDTO(BigInteger.ONE, "produto test", Double.valueOf(10.00), BigDecimal.valueOf(12.99), true);
      String resultado = produtoService.excluirProduto(dto.getId());
      assertEquals("Excluído", resultado);
  }

}
