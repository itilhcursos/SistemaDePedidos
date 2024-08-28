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
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoPrecoNegativoException;


@SpringBootTest
@ActiveProfiles("test")
public class ProdutoServiceTest {

    @Autowired
    ProdutoService produtoService;


    @Test
    @DisplayName("teste de criar Produto")
    void testCriarProduto() throws Exception {

        ProdutoDTO dto = new ProdutoDTO(null, "Produto teste", 100.00, BigDecimal.valueOf(18.08), true );
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dto);

        assertEquals(true, dtoRetorno.getId() != null);

    }

    @Test
    @DisplayName("teste de criar Produto duplicado")
    void testCriarProdutoDuplicado() throws Exception {

        ProdutoDTO dto = new ProdutoDTO(null, "Produto teste", 100.00, BigDecimal.valueOf(18.08), true );
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dto);


       assertThrows(ProdutoDuplicadoException.class, ()-> produtoService.criarProduto(dto));

    }

    @Test
    @DisplayName("test de Criar Produto com Estoque negativo")
    void testProdutoEstoqueNegativo() throws Exception{
        ProdutoDTO dto = new ProdutoDTO(null, "Produto teste", 0.0, BigDecimal.valueOf(18.08), true );

        assertThrows(ProdutoEstoqueNegativoException.class, ()-> produtoService.criarProduto(dto));
    }


    @Test
    void testAlterarProdutoEstoqueNegativo() throws Exception {
        ProdutoDTO dtoDescricaoErrada = new ProdutoDTO(null, " test errado", 1.0, BigDecimal.valueOf(18.08), true );
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dtoDescricaoErrada);

        ProdutoDTO dtoDescricaoCorrigida = new ProdutoDTO(dtoRetorno.getId(), "teste corrigido", 0.0, BigDecimal.valueOf(18.08), true );
       
        assertThrows(ProdutoEstoqueNegativoException.class, ()-> produtoService.alterarProduto(dtoDescricaoCorrigida.getId(), dtoDescricaoCorrigida));
    }

    @Test
    @DisplayName("test de Criar Produto com o Preço negativo")
    void testProdutoPrecoNegativo() throws Exception{
        ProdutoDTO dto = new ProdutoDTO(null, "Produto teste", 100.00, BigDecimal.valueOf(0), true );

        assertThrows(ProdutoPrecoNegativoException.class, ()-> produtoService.criarProduto(dto));
    }

    @Test
    @DisplayName("test de Alteração de Produto com o Preço negativo")
    void testAlterarProdutoPrecoNegativo() throws Exception {
        ProdutoDTO dtoDescricaoErrada = new ProdutoDTO(null, " test errado", 100.00, BigDecimal.valueOf(18.08), true );
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dtoDescricaoErrada);

        ProdutoDTO dtoDescricaoCorrigida = new ProdutoDTO(dtoRetorno.getId(), "teste corrigido", 100.00, BigDecimal.valueOf(0), true );
       
        assertThrows(ProdutoPrecoNegativoException.class, ()-> produtoService.alterarProduto(dtoDescricaoCorrigida.getId(), dtoDescricaoCorrigida));
    }

}
