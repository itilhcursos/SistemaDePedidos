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
    @DisplayName("Teste Produto Alterado")
    void testAlterarProduto() throws Exception {
        ProdutoDTO dtoProdutoErrado = new ProdutoDTO(null, "Produto Cadastrado Errado", 20.00,
                BigDecimal.valueOf(45.87), true);
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dtoProdutoErrado);

        ProdutoDTO dtoProdutoCorreto = new ProdutoDTO(dtoRetorno.getId(), "Produto Correto", 20.00,
                BigDecimal.valueOf(45.87), true);
        dtoProdutoCorreto = produtoService.alterarProduto(dtoRetorno.getId(), dtoProdutoCorreto);
        assertEquals(true, dtoProdutoCorreto.getId().equals(dtoRetorno.getId()));

    }

    @Test
    @DisplayName("Teste Protuto Alterar Produto Duplicado")
    void testAlterarProdutoDuplicado() throws Exception {
        ProdutoDTO dtoProdutoErrado = new ProdutoDTO(null, "Produto Cadastrado Errado", 7.00, BigDecimal.valueOf(67.99),
                true);
        dtoProdutoErrado = produtoService.criarProduto(dtoProdutoErrado);

        ProdutoDTO dtoProdutoCerto = new ProdutoDTO(null, "Produto Correto", 25.00, BigDecimal.valueOf(32.59), true);
        dtoProdutoCerto = produtoService.criarProduto(dtoProdutoCerto);

        ProdutoDTO dtoProdutoCorrigido = new ProdutoDTO(dtoProdutoCerto.getId(), "Produto Correto", 25.00,
                BigDecimal.valueOf(32.59), true);
        assertThrows(ProdutoDuplicadoException.class,
                () -> produtoService.alterarProduto(dtoProdutoCorrigido.getId(), dtoProdutoCorrigido));
    }

    @Test
    @DisplayName("Teste Criar Produto")
    void testCriarProduto() throws Exception {
        ProdutoDTO dto = new ProdutoDTO(null, "Protuto1", 7.00, BigDecimal.valueOf(59.99), true);
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dto);
        assertEquals(true, dtoRetorno.getId() != null);
    }

    @Test
    @DisplayName("Produto preco Negativo")
    void testCriarProdutoPrecoNegativo() throws Exception {
        ProdutoDTO dto = new ProdutoDTO(null, "Produto Teste", 5.00, BigDecimal.valueOf(-23.50), true);
        assertThrows(ProdutoEstoqueNegativoException.class, () -> produtoService.criarProduto(dto));
    }

    @Test
    @DisplayName("Alterar Preco Negativo")
    void testAlterarPrecoNegativo() throws Exception {
        ProdutoDTO dtoPrecoErrado = new ProdutoDTO(null, "Preco Errado", 43.00, BigDecimal.valueOf(55.40), true);
        dtoPrecoErrado = produtoService.criarProduto(dtoPrecoErrado);

        ProdutoDTO dtoPrecoCerto = new ProdutoDTO(null, "Preco Correto", 55.00, BigDecimal.valueOf(23.55), true);
        dtoPrecoCerto = produtoService.criarProduto(dtoPrecoCerto);

        ProdutoDTO dtoPrecoCorreto = new ProdutoDTO(dtoPrecoErrado.getId(), "Preco Corrigido", 32.00,
                BigDecimal.valueOf(-45.55), true);
        assertThrows(ProdutoEstoqueNegativoException.class ,
                () -> produtoService.alterarProduto(dtoPrecoCorreto.getId(), dtoPrecoCorreto));
    }

    @Test
    @DisplayName("Produto Quantidade Negativa")
    void testCriarProdutoQuantidadeNegativo() throws Exception {
        ProdutoDTO dto = new ProdutoDTO(null, "Teste Produto", -15.00, BigDecimal.valueOf(54.79), true);
        assertThrows(ProdutoEstoqueNegativoException.class, () -> produtoService.criarProduto(dto));
    }

    @Test
    @DisplayName("Alterar Estoque Negativo")
    void testAlterarEstoqueNegativo() throws Exception {
        ProdutoDTO dtoEstoqueErrado = new ProdutoDTO(null, "Estoque Errado", 3.00, BigDecimal.valueOf(33.33), true);
        dtoEstoqueErrado = produtoService.criarProduto(dtoEstoqueErrado);

        ProdutoDTO dtoEstoqueCerto = new ProdutoDTO(null, "Estoque Certo", 23.00, BigDecimal.valueOf(44.44), true);
        dtoEstoqueCerto = produtoService.criarProduto(dtoEstoqueCerto);

        ProdutoDTO dtoEstoqueCorreto = new ProdutoDTO(dtoEstoqueErrado.getId(), "Estoque Correto", -65.00,
                BigDecimal.valueOf(22.00), true);
        assertThrows(ProdutoEstoqueNegativoException.class,
                () -> produtoService.alterarProduto(dtoEstoqueCorreto.getId(), dtoEstoqueCorreto));
    }

    @Test
    @DisplayName("ProdutoDuplicado")
    void testCriarProdutoDuplicado() throws Exception {
        ProdutoDTO dto = new ProdutoDTO(null, "Teste Produto", 50.00, BigDecimal.valueOf(45.99), true);
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dto);
        assertThrows(ProdutoDuplicadoException.class, () -> produtoService.criarProduto(dto));
    }
}
