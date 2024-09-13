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
    @DisplayName("teste do Serviço de Alterar um Produto com Preço válido para um Preço Negativo")
    void testAlterarProdutoCriadoPrecoNegativo() throws Exception {
        ProdutoDTO dtoPrecoErrado = new ProdutoDTO(null, "Produto teste", 1.0, BigDecimal.valueOf(200), false);
        dtoPrecoErrado = produtoService.criarProduto(dtoPrecoErrado);

        ProdutoDTO dtoPrecoCorrigido = new ProdutoDTO(dtoPrecoErrado.getId(), "Produto teste", 1.0, BigDecimal.valueOf(-222), false);
        assertThrows(ProdutoPrecoNegativoException.class, ()-> produtoService.alterarProduto(dtoPrecoCorrigido.getId(), dtoPrecoCorrigido));
    }

    @Test
    @DisplayName("teste do Serviço de Alterar um Produto com Estoque válido para um Estoque Negativo")
    void testAlterarProdutoCriadoEstoqueNegativo() throws Exception {
        ProdutoDTO dtoEstoqueErrado = new ProdutoDTO(null, "Produto teste", 1.0, BigDecimal.valueOf(200), false);
        dtoEstoqueErrado = produtoService.criarProduto(dtoEstoqueErrado);

        ProdutoDTO dtoEstoqueCorrigido = new ProdutoDTO(dtoEstoqueErrado.getId(), "Produto teste", -1.0, BigDecimal.valueOf(200), false);
        assertThrows(ProdutoEstoqueNegativoException.class, ()-> produtoService.alterarProduto(dtoEstoqueCorrigido.getId(), dtoEstoqueCorrigido));
    }

    @Test
    @DisplayName("teste do Serviço de Criar um Produto com Preço Negativo")
    void testCriarProdutoPrecoNegativo() throws Exception {
        ProdutoDTO dto = new ProdutoDTO(null, "Produto teste", 1.0, BigDecimal.ONE.negate(), false);
        assertThrows(ProdutoPrecoNegativoException.class, ()-> produtoService.criarProduto(dto));
    }
    
    @Test
    @DisplayName("teste do Serviço de Criar um Produto com Estoque Negativo")
    void testCriarProdutoEstoqueNegativo() throws Exception {
        ProdutoDTO dto = new ProdutoDTO(null, "Produto teste", -1.0, BigDecimal.valueOf(200), false); 
        assertThrows(ProdutoEstoqueNegativoException.class, ()-> produtoService.criarProduto(dto));
    }

    @Test
    @DisplayName("teste do Serviço de Alterar o Produto criado")
    void testAlterarProdutoCriado() throws Exception {
        ProdutoDTO dtoDescricaoErrada = new ProdutoDTO(null, "Descrição errada", 1.0, BigDecimal.valueOf(200), false);
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dtoDescricaoErrada);

        ProdutoDTO dtoDescricaoCorrigida = new ProdutoDTO(dtoRetorno.getId(), "Descrição corrigida", 1.0, BigDecimal.valueOf(200), false);
        dtoDescricaoCorrigida = produtoService.alterarProduto(dtoRetorno.getId(), dtoDescricaoCorrigida);

        assertEquals(true, dtoDescricaoCorrigida.getId().equals(dtoRetorno.getId()));
    }

    @Test
    @DisplayName("teste do Serviço de Alterar o Produto criado para criar um Produto Duplicado")
    void testAlterarProdutoCriadoDuplicado() throws Exception {
        ProdutoDTO dtoDescricaoErrada = new ProdutoDTO(null, "Descrição errada", 1.0, BigDecimal.valueOf(200), false);
        dtoDescricaoErrada = produtoService.criarProduto(dtoDescricaoErrada);

        ProdutoDTO dtoCorrigido = new ProdutoDTO(null, "Descrição corrigida", 1.0, BigDecimal.valueOf(200), false);
        dtoCorrigido = produtoService.criarProduto(dtoCorrigido);

        ProdutoDTO dtoDescricaoCorrigida = new ProdutoDTO(dtoDescricaoErrada.getId(), "Descrição corrigida", 1.0, BigDecimal.valueOf(200), false);
        assertThrows(ProdutoDuplicadoException.class, ()-> produtoService.alterarProduto(dtoDescricaoCorrigida.getId(), dtoDescricaoCorrigida));
    }

    @Test
    @DisplayName("teste do Serviço de Criar um novo Produto")
    void testCriarProduto() throws Exception {
        ProdutoDTO dto = new ProdutoDTO(null, "Produto teste", 1.0, BigDecimal.valueOf(200), false);
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dto);
        assertEquals(true, dtoRetorno.getId() != null);
    }

    @Test
    @DisplayName("teste do Serviço de Criar um Produto Duplicado")
    void testCriarProdutoDuplicado() throws Exception {
        ProdutoDTO dto = new ProdutoDTO(null, "Produto teste duplicado", 1.0, BigDecimal.valueOf(200), false);
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dto);
        assertThrows(ProdutoDuplicadoException.class, ()-> produtoService.criarProduto(dto));
    }

        //faltou testar os demais métodos da service
}
