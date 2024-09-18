package br.com.itilh.bdpedidos.sistemapedidos.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ProdutoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoPrecoUnidadeAtualException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoQuantidadeEstoqueException;

@SpringBootTest
@ActiveProfiles("test")
public class ProdutoServiceTest {

    @Autowired
    private ProdutoService produtoService;

    @Test
    @DisplayName("Teste de regra de negócio - Produto com Estoque Negativo")
    void testCriarProdutoComEstoqueNegativo() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO(null, "Produto Teste", -1.0, new BigDecimal ("100.00"), true);
        assertThrows(ProdutoQuantidadeEstoqueException.class, () -> produtoService.criarProduto(produtoDTO));
    }

    @Test
    @DisplayName("Teste de regra de negócio - Alterar Produto com Estoque Negativo")
    void testAlterarProdutoComEstoqueNegativo() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO(BigInteger.ONE, "Produto Teste", -3.0, new BigDecimal("100.00"), true);
        assertThrows(ProdutoQuantidadeEstoqueException.class, () -> produtoService.alterarProduto(BigInteger.ONE, produtoDTO));
    }

    
    @Test
    @DisplayName("Teste de regra de negócio - Produto com Preço Negativo")
    void testCriarProdutoComPrecoNegativo() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO(null, "Produto Teste", 10.0, new BigDecimal ("-50.00"), true);
        assertThrows(ProdutoPrecoUnidadeAtualException.class, () -> produtoService.criarProduto(produtoDTO));
    }

    @Test
    @DisplayName("Teste de regra de negócio - Alterar Produto com Preço Negativo")
    void testAlterarProdutoComPrecoNegativo() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO(BigInteger.ONE, "Produto Teste", 20.0, new BigDecimal("-100.00"), true);
        assertThrows(ProdutoPrecoUnidadeAtualException.class, () -> produtoService.alterarProduto(BigInteger.ONE, produtoDTO));
    }

    //faltou testar os demais métodos da classe service
    //faltou testar o produto duplicado

}

