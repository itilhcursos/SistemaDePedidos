package br.com.itilh.bdpedidos.sistemapedidos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
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
    @DisplayName("teste de buscar produto por id")
    void testBuscarProdutoPorId() throws Exception{
        setupProduto();
        ProdutoDTO produtodto = produtoService.buscarProdutoPorId(BigInteger.ONE);
        assertEquals(BigInteger.ONE, produtodto.getId());
    }

@Test
@DisplayName("teste de produto id nao encontrado")
void  testProdutoIDNaoEncontrado()throws Exception{
    setupProduto();
    ProdutoDTO produtodto = produtoService.buscarProdutoPorId(BigInteger.valueOf(586486486486L));
    assertEquals(null, produtodto);

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
    @DisplayName("teste de excluir produto")
    void testExcluirProduto() throws Exception {
        setupProduto();
        produtoService.excluirProduto(BigInteger.ONE);
        
        ProdutoDTO produtodto = produtoService.buscarProdutoPorId(BigInteger.ONE);
        assertNull(produtodto);
    }

@Test
@DisplayName("teste de excluir produto nao existente")
    void testExcluirProdutoNaoExistente() throws Exception {
        setupProduto();
        produtoService.excluirProduto(BigInteger.TEN);
        ProdutoDTO produtodto = produtoService.buscarProdutoPorId(BigInteger.ONE);
        assertEquals(BigInteger.ONE,produtodto);


    }

    @Test
    @DisplayName(" teste de listar produtos")
    void testListarProdutos() throws Exception {

        Page<ProdutoDTO> produtosList = produtoService.listarProdutos(null);
    
    assertEquals(null, produtosList);
    
        ProdutoDTO produto1 = null;


        for (ProdutoDTO produto : produtosList){
            if (produto.getId().equals(BigInteger.ONE)){
                produto1 = produto;  }
       
            }
            assertNull(produto1);
            assertEquals(BigInteger.ONE, produto1.getId());
            assertEquals("produto teste", produto1.getDescricao());
            assertEquals(Double.valueOf(1), produto1.getQuantidadeEstoque());
            assertEquals(BigDecimal.valueOf(1), produto1.getPrecoUnidadeAtual());
        assertEquals(true, produto1.getAtivo());
        }
       
    

}
