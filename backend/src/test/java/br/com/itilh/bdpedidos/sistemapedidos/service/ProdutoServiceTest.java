package br.com.itilh.bdpedidos.sistemapedidos.service;

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
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoPrecoNegativoException;

@SpringBootTest
@ActiveProfiles("test")
public class ProdutoServiceTest {

    @Autowired
    ProdutoService produtoService;

    @Test
    @DisplayName("Produto Duplicado")
    void testCriarProdutoDuplicado() throws Exception {
        ProdutoDTO dto = new ProdutoDTO(null,"Produto teste duplicado",1.0, BigDecimal.valueOf(10), true);
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dto);

        assertThrows(ProdutoDuplicadoException.class, ()-> produtoService.criarProduto(dto));
    }

    @Test
    @DisplayName("Teste de alteração de nome duplicado")
    void testCriarProdutoDuplicadoAlterar() throws Exception {
        
        ProdutoDTO dtoNomeErrado = new ProdutoDTO(null,"Nome errado",1.0, BigDecimal.valueOf(10), true);
        dtoNomeErrado = produtoService.criarProduto(dtoNomeErrado);
        
        ProdutoDTO dtoCorrigido = new ProdutoDTO(null,"Nome corrigido",1.0, BigDecimal.valueOf(10), true);
        dtoCorrigido = produtoService.criarProduto(dtoCorrigido);

        ProdutoDTO dtoNomeCorrigido = new ProdutoDTO(dtoNomeErrado.getId(),"Nome corrigido",1.0, BigDecimal.valueOf(10), true);
        assertThrows(ProdutoDuplicadoException.class, ()-> produtoService.alterarProduto(dtoNomeCorrigido.getId(), dtoNomeCorrigido));
    }

    @Test
    @DisplayName("Teste de criar produto com estoque negativo")
    void testCriarProdutoEstoqueNegativo() throws Exception {
        ProdutoDTO dto = new ProdutoDTO(null,"Produto teste estoque negativo",-1.0, BigDecimal.valueOf(10), true);

        assertThrows(ProdutoEstoqueNegativoException.class, ()-> produtoService.criarProduto(dto));
    }

    @Test
    @DisplayName("Teste de alteração com estoque negativo")
    void testAlterarProdutoEstoqueNegativo() throws Exception {
        
        ProdutoDTO dtoNomeErrado = new ProdutoDTO(null,"Nome errado",1.0, BigDecimal.valueOf(10), true);
        dtoNomeErrado = produtoService.criarProduto(dtoNomeErrado);
        
        ProdutoDTO dtoCorrigido = new ProdutoDTO(null,"Nome corrigido",1.0, BigDecimal.valueOf(10), true);
        dtoCorrigido = produtoService.criarProduto(dtoCorrigido);

        ProdutoDTO dtoNomeCorrigido = new ProdutoDTO(dtoNomeErrado.getId(),"Nome corrigido2",-1.0, BigDecimal.valueOf(10), true);
        assertThrows(ProdutoEstoqueNegativoException.class, ()-> produtoService.alterarProduto(dtoNomeCorrigido.getId(), dtoNomeCorrigido));
    }

    @Test
    @DisplayName("Teste de criar produto com preço negativo")
    void testCriarProdutoPrecoNegativo() throws Exception {
        ProdutoDTO dto = new ProdutoDTO(null,"Produto teste estoque negativo",1.0, BigDecimal.valueOf(-10), true);

        assertThrows(ProdutoPrecoNegativoException.class, ()-> produtoService.criarProduto(dto));
    }

    @Test
    @DisplayName("Teste de alteração com preco negativo")
    void testAlterarProdutoPrecoNegativo() throws Exception {
        
        ProdutoDTO dtoNomeErrado = new ProdutoDTO(null,"Nome errado",1.0, BigDecimal.valueOf(10), true);
        dtoNomeErrado = produtoService.criarProduto(dtoNomeErrado);
        
        ProdutoDTO dtoCorrigido = new ProdutoDTO(null,"Nome corrigido",1.0, BigDecimal.valueOf(10), true);
        dtoCorrigido = produtoService.criarProduto(dtoCorrigido);

        ProdutoDTO dtoNomeCorrigido = new ProdutoDTO(dtoNomeErrado.getId(),"Nome corrigido2",1.0, BigDecimal.valueOf(-10), true);
        assertThrows(ProdutoPrecoNegativoException.class, ()-> produtoService.alterarProduto(dtoNomeCorrigido.getId(), dtoNomeCorrigido));
    }
}
