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
    void testAlterarProduto() {

    }

    @Test
    void testBuscarProdutoPorId() {

    }

    @Test
    @DisplayName(" teste do criar produto")
    void testCriarProduto() throws Exception{
        setupProduto();
     ProdutoDTO dto = new ProdutoDTO(null,"produto teste",Double.valueOf(1), BigDecimal.valueOf(1), true);
     ProdutoDTO dtoRetorno =produtoService.criarProduto(dto);
    assertEquals(true,dtoRetorno.getId() != null);

    }
    @Test
    @DisplayName(" teste do criar produto  duplicado")
void testCriarProdutoDuplicado()throws Exception{
    setupProduto();
    ProdutoDTO dto = new ProdutoDTO(null,"produto teste duplicado",Double.valueOf(1), BigDecimal.valueOf(1), true);
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
