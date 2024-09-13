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
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoEstoqueNegativoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoPrecoNegativoException;

@SpringBootTest
@ActiveProfiles("test")
public class ProdutoServiceTest {

    @Autowired
    ProdutoRepository produtoRepository;    
    
    @Autowired
    ProdutoService produtoService;

    void setupProduto() {
        Produto produto = new Produto(
            BigInteger.ONE, 
            "Produto de Teste",
            Double.valueOf(9), 
            BigDecimal.valueOf(9), 
            true
        );
        produtoRepository.save(produto);
    }

    @Test
    void testCriarProduto() throws Exception {
        ProdutoDTO dto = new ProdutoDTO(
            null, 
            "Produto de Teste", 
            Double.valueOf(9), 
            BigDecimal.valueOf(9), 
            true
        );
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dto);
        assertEquals(true, dtoRetorno.getId() != null);
    }

    @Test
    @DisplayName("Teste de regra de negócio -  Produto Duplicado")
    void testCriarProdutoDuplicado() throws Exception {

        setupProduto();

        ProdutoDTO dto = new ProdutoDTO(
            null, 
            "Produto Duplicado", 
            Double.valueOf(9), 
            BigDecimal.valueOf(9),
            true
        );
    //  ProdutoDTO dtoRetorno = produtoService.criarProduto(dto);
        
        assertThrows(ProdutoDuplicadoException.class, () -> produtoService.criarProduto(dto));
    }

    @Test
    @DisplayName("test de criação do produto com estoque negativo")
    void testProdutoEstoqueNegativo() throws Exception {
        ProdutoDTO dto = new ProdutoDTO(null, "Produto teste", 0.0, BigDecimal.valueOf(18.08), true );

        assertThrows(ProdutoEstoqueNegativoException.class, ()-> produtoService.criarProduto(dto));
    }


    @Test
    void testAlterarProdutoEstoqueNegativo() throws Exception {
        ProdutoDTO dtoDescricaoErrada = new ProdutoDTO(null, " test errado", 1.0, BigDecimal.valueOf(18.03), true );
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dtoDescricaoErrada);

        ProdutoDTO dtoDescricaoCorrigida = new ProdutoDTO(dtoRetorno.getId(), "teste corrigido", 0.0, BigDecimal.valueOf(17.08), true );
       
        assertThrows(ProdutoEstoqueNegativoException.class, ()-> produtoService.alterarProduto(dtoDescricaoCorrigida.getId(), dtoDescricaoCorrigida));
    }

    @Test
    @DisplayName("test de Criar Produto com o Preço negativo")
    void testProdutoPrecoNegativo() throws Exception{
        ProdutoDTO dto = new ProdutoDTO(null, "Produto teste", 200.00, BigDecimal.valueOf(0), true );

        assertThrows(ProdutoPrecoNegativoException.class, ()-> produtoService.criarProduto(dto));
    }

    @Test
    @DisplayName("test de alteração do produto com um preço negativo")
    void testAlterarProdutoPrecoNegativo() throws Exception {
        ProdutoDTO dtoDescricaoErrada = new ProdutoDTO(null, " test errado", 100.00, BigDecimal.valueOf(20.09), true );
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dtoDescricaoErrada);

        ProdutoDTO dtoDescricaoCorrigida = new ProdutoDTO(dtoRetorno.getId(), "teste corrigido", 100.00, BigDecimal.valueOf(0), true );
       
        assertThrows(ProdutoPrecoNegativoException.class, ()-> produtoService.alterarProduto(dtoDescricaoCorrigida.getId(), dtoDescricaoCorrigida));
    }

        //faltou os testes dos outros métodos da service
}