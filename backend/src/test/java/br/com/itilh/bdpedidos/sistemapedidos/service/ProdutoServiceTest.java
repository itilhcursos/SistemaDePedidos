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
import org.springframework.test.context.ContextConfiguration;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ProdutoDTO;
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
        Produto produto = new Produto(BigInteger.ONE, "produto teste",Double.valueOf(1), BigDecimal.valueOf(1), true);
        produtoRepository.save(produto);
    }

    
    @Test
    @DisplayName(" teste do produto duplicado por alteração")
    void testAlterarProdutoDuplicado() throws Exception {
        setupProduto();
        ProdutoDTO dtoDescricaoErrado = new ProdutoDTO(BigInteger.ONE,"produto errado",Double.valueOf(1), BigDecimal.valueOf(1), true);
         dtoDescricaoErrado =produtoService.criarProduto(dtoDescricaoErrado);
    
        ProdutoDTO dtoDescricaoCorrigido = new ProdutoDTO(null,"produto corrigido",Double.valueOf(1), BigDecimal.valueOf(1), true);
        dtoDescricaoCorrigido = produtoService.criarProduto(dtoDescricaoCorrigido);
   
        ProdutoDTO dtoCorrigido = new ProdutoDTO(dtoDescricaoErrado.getId(),"produto corrigido",Double.valueOf(1), BigDecimal.valueOf(1), true);
   
        assertThrows(ProdutoDuplicadoException.class,()-> produtoService.alterarProduto(dtoCorrigido.getId(), dtoCorrigido));
    }

@Test
@DisplayName("teste de alterar produto")
void testAlterarProduto() throws Exception {
    setupProduto();
    ProdutoDTO dtoDescricaoErrado = new ProdutoDTO(BigInteger.ONE,"produto errado",Double.valueOf(1), BigDecimal.valueOf(1), true);
    ProdutoDTO dtoRetorno =produtoService.criarProduto(dtoDescricaoErrado);
    ProdutoDTO dtoDescricaoCorrigido = new ProdutoDTO(dtoRetorno.getId(),"produto corrigido",Double.valueOf(1), BigDecimal.valueOf(1), true);
   dtoDescricaoCorrigido = produtoService.alterarProduto(dtoRetorno.getId(), dtoDescricaoCorrigido);
    assertEquals(true,dtoDescricaoCorrigido.getId().equals(dtoRetorno.getId()));
}

    @Test
    void testBuscarProdutoPorId() {

    }

    @Test
    @DisplayName(" teste do criar produto")
    void testCriarProduto() throws Exception{
        setupProduto();
     ProdutoDTO dto = new ProdutoDTO(BigInteger.ONE,"produto teste",Double.valueOf(1), BigDecimal.valueOf(1), true);
     ProdutoDTO dtoRetorno =produtoService.criarProduto(dto);
    assertEquals(true,dtoRetorno.getId() != null);

    }
    @Test
    @DisplayName(" teste do criar produto  duplicado")
void testCriarProdutoDuplicado()throws Exception{
    setupProduto();
    ProdutoDTO dto = new ProdutoDTO(BigInteger.ONE,"produto teste duplicado",Double.valueOf(1), BigDecimal.valueOf(1), true);
    ProdutoDTO dtoRetorno =produtoService.criarProduto(dto);


assertThrows(ProdutoDuplicadoException.class,()-> produtoService.criarProduto(dto));

}


    @Test
    void testExcluirProduto() {

    }

    @Test
    void testListarProdutos() {

    }
}
