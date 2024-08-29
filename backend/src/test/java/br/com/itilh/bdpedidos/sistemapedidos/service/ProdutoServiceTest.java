package br.com.itilh.bdpedidos.sistemapedidos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
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
    private ProdutoService produtoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @TestConfiguration
    static class ProdutoServiceTestConfiguration {
        @Bean
        public ProdutoService produtoService() {
            return new ProdutoService();
        }
    }

    @Test
    void testCriarProduto() throws Exception {
        ProdutoDTO dto = new ProdutoDTO(null, "Produto Teste", Double.valueOf(100), BigDecimal.valueOf(10), true);
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dto);
        assertNotNull(dtoRetorno.getId());
    }

    @Test
    @DisplayName("Teste de regra de negócio - Produto Duplicado")
    void testCriarProdutoDuplicado() throws Exception {
        ProdutoDTO dto = new ProdutoDTO(null, "Produto Duplicado", Double.valueOf(100), BigDecimal.valueOf(10), true);

        assertThrows(ProdutoDuplicadoException.class,
                () -> produtoService.criarProduto(dto));
    }

    @Test
    @DisplayName("Teste de regra de negócio - Estoque Negativo")
    void testCriarProdutoComEstoqueNegativo() {
        ProdutoDTO dto = new ProdutoDTO(null, "Produto Estoque Negativo", Double.valueOf(100), BigDecimal.valueOf(10),
                true);

        assertThrows(ProdutoEstoqueNegativoException.class,
                () -> produtoService.criarProduto(dto));
    }

    @Test
    @DisplayName("Teste de regra de negócio - Preço Negativo")
    void testCriarProdutoComPrecoNegativo() {
        ProdutoDTO dto = new ProdutoDTO(null, "Produto Preço Negativo", Double.valueOf(100), BigDecimal.valueOf(10),
                true);

        assertThrows(ProdutoPrecoNegativoException.class,
                () -> produtoService.criarProduto(dto));
    }

    @Test
    @DisplayName("Teste de alteração de produto")
    void testAlterarProduto() throws Exception {
        ProdutoDTO dtoOriginal = new ProdutoDTO(null, "Produto Original", Double.valueOf(100), BigDecimal.valueOf(10),
                true);
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dtoOriginal);


        ProdutoDTO dtoAlterado = new ProdutoDTO(dtoRetorno.getId(), "Produto alterado",Double.valueOf(100), BigDecimal.valueOf(10),
        true);
        dtoAlterado = produtoService.alterarProduto(dtoRetorno.getId(), dtoAlterado);
        assertEquals("Forma de pagamento alterada", dtoAlterado.getDescricao());
    }

    @Test
    @DisplayName("Teste de exclusão de produto")
    void testExcluirProduto() throws Exception {
        // ProdutoDTO dto = new ProdutoDTO(BigInteger.ONE, "Produto a Excluir", Double.valueOf(100),
        //         BigDecimal.valueOf(10), true);

        when(produtoRepository.findById(BigInteger.ONE)).thenReturn(Optional.of(new Produto()));

        String resultado = produtoService.excluirProduto(BigInteger.ONE);
        assertEquals("Excluído", resultado);
    }

    @Test
    @DisplayName("Teste de busca por ID existente")
    void testBuscarProdutoPorIdExistente() throws Exception {
        ProdutoDTO dto = new ProdutoDTO(BigInteger.ONE, "Produto Existente", Double.valueOf(100),
                BigDecimal.valueOf(10), true);

        ProdutoDTO dtoBuscado = produtoService.buscarProdutoPorId(BigInteger.ONE);
        assertEquals(dto.getDescricao(), dtoBuscado.getDescricao());
    }

    @Test
    @DisplayName("Teste de busca por ID inexistente")
    void testBuscarProdutoPorIdInexistente() {
        when(produtoRepository.findById(BigInteger.valueOf(999999L))).thenReturn(Optional.empty());

        assertThrows(IdInexistenteException.class,
                () -> produtoService.buscarProdutoPorId(BigInteger.valueOf(999999L)));
    }
}
