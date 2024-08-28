package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    @DisplayName("teste de criar Produto")
    void testCriarProduto() throws Exception {

        ProdutoDTO dto = new ProdutoDTO(null, "Produto teste", 100.00, BigDecimal.valueOf(18.08), true );
        dto = produtoService.criarProduto(dto);

        assertEquals(true, dto.getId() != null);

    }

    @Test
    @DisplayName("teste de criar Produto duplicado")
    void testCriarProdutoDuplicado() throws Exception {

        ProdutoDTO dto = new ProdutoDTO(null, "Produto teste", 100.00, BigDecimal.valueOf(18.08), true );
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dto);


       assertThrows(ProdutoDuplicadoException.class, ()-> produtoService.criarProduto(dtoRetorno));

    }

    @Test
    @DisplayName("test de Estoque negativo de Produto")
    void testProdutoEstoqueNegativo() throws Exception{
        ProdutoDTO dto = new ProdutoDTO(null, "Produto teste", Double.valueOf(0), BigDecimal.valueOf(18.08), true );
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dto);

        assertThrows(ProdutoEstoqueNegativoException.class, ()-> produtoService.criarProduto(dtoRetorno));
    }

    @Test
    void testAlterarProduto() {

    }

}
