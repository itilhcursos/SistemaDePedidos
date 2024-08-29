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
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoPrecoNegativoException;

@SpringBootTest
@ActiveProfiles("test")
public class ProdutoServiceTest {

    @Autowired
    ProdutoService produtoService;

    @Test
    void testCriarProduto() throws Exception{
        ProdutoDTO dto = new ProdutoDTO(null, "Produto teste",10.00,BigDecimal.valueOf(100.00), true);
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dto);
        assertEquals(true, dtoRetorno.getId() != null); 
    }

    @Test
    @DisplayName("Teste Criar Produto Estoque Negativo")
    void testCriarProdutoEstoqueNegativo() throws Exception{
        ProdutoDTO dto = new ProdutoDTO(null, "Produto teste estoque negativo",-1.00,BigDecimal.valueOf(100.00), true);
        assertThrows(ProdutoEstoqueNegativoException.class, ()-> produtoService.criarProduto(dto));
    }

    @Test
    @DisplayName("Teste Criar Produto Preço Negativo")
    void testCriarProdutoPrecoNegativo() throws Exception{
        ProdutoDTO dto = new ProdutoDTO(null, "Produto teste preço negativo",10.00,BigDecimal.valueOf(-100.00), true);
        assertThrows(ProdutoPrecoNegativoException.class, ()-> produtoService.criarProduto(dto));
    }

    @Test
    @DisplayName("Teste de regra de negócio -  Produto Duplicado")
    void testCriarProdutoDuplicado() throws Exception {
        ProdutoDTO dto = new ProdutoDTO(null, "Produto teste duplicado",10.00,BigDecimal.valueOf(100.00), true);
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dto);
        assertThrows(ProdutoDuplicadoException.class, ()-> produtoService.criarProduto(dto));
    }

    @Test
    @DisplayName("Teste de alteração de descrição")
    void testCriarProdutoAlterar() throws Exception {
        ProdutoDTO dtoDescricaoErrada = new ProdutoDTO(null, "Descrição errada",10.00,BigDecimal.valueOf(100.00), true);

        ProdutoDTO dtoRetorno = produtoService.criarProduto(dtoDescricaoErrada);

        ProdutoDTO dtoDescricaoCorrigida = new ProdutoDTO(dtoRetorno.getId(),"Descrição corrigida",10.00,BigDecimal.valueOf(100.00),true);
        dtoDescricaoCorrigida = produtoService.alterarProduto(dtoRetorno.getId(), dtoDescricaoCorrigida);
        assertEquals(true, dtoDescricaoCorrigida.getId().equals(dtoRetorno.getId()));
    }

    @Test
    @DisplayName("Teste de alteração de descrição duplicada")
    void testCriarProdutoDuplicadoAlterar() throws Exception {
        ProdutoDTO dtoDescricaoErrada = new ProdutoDTO(null, "Descrição errada",10.00,BigDecimal.valueOf(100.00), true);
        dtoDescricaoErrada = produtoService.criarProduto(dtoDescricaoErrada);
    
        ProdutoDTO dtoCorrigido = new ProdutoDTO(null, "Descrição corrigida",10.00,BigDecimal.valueOf(100.00), true);
        dtoCorrigido = produtoService.criarProduto(dtoCorrigido);

        ProdutoDTO dtoDescricaoCorrigida = new ProdutoDTO(dtoDescricaoErrada.getId(),"Descrição corrigida",10.00,BigDecimal.valueOf(100.00),true);
        assertThrows(ProdutoDuplicadoException.class, ()-> produtoService.alterarProduto(dtoDescricaoCorrigida.getId(), dtoDescricaoCorrigida));
    }

    @Test
    @DisplayName("Teste Estoque Negativo")
    void testAlterarProdutoEstoqueNegativo() throws Exception {
        ProdutoDTO dtoDescricaoErrada = new ProdutoDTO(null, "Descrição errada",10.00,BigDecimal.valueOf(100.00), true);
        dtoDescricaoErrada = produtoService.criarProduto(dtoDescricaoErrada);
    
        ProdutoDTO dtoCorrigido = new ProdutoDTO(null, "Descrição corrigida",10.00,BigDecimal.valueOf(100.00), true);
        dtoCorrigido = produtoService.criarProduto(dtoCorrigido);

        ProdutoDTO dtoDescricaoCorrigida = new ProdutoDTO(dtoDescricaoErrada.getId(),"Descrição corrigida 2x",-10.00,BigDecimal.valueOf(100.00),true);
        assertThrows(ProdutoEstoqueNegativoException.class, ()-> produtoService.alterarProduto(dtoDescricaoCorrigida.getId(), dtoDescricaoCorrigida));
    }

    @Test
    @DisplayName("Teste alterar Preco Negativo")
    void testAlterarProdutoPrecoNegativo() throws Exception {
        ProdutoDTO dtoDescricaoErrada = new ProdutoDTO(null, "Descrição errada",10.00,BigDecimal.valueOf(100.00), true);
        dtoDescricaoErrada = produtoService.criarProduto(dtoDescricaoErrada);
    
        ProdutoDTO dtoCorrigido = new ProdutoDTO(null, "Descrição corrigida",10.00,BigDecimal.valueOf(100.00), true);
        dtoCorrigido = produtoService.criarProduto(dtoCorrigido);

        ProdutoDTO dtoDescricaoCorrigida = new ProdutoDTO(dtoDescricaoErrada.getId(),"Descrição corrigida 2x",10.00,BigDecimal.valueOf(-100.00),true);
        assertThrows(ProdutoPrecoNegativoException.class, ()-> produtoService.alterarProduto(dtoDescricaoCorrigida.getId(), dtoDescricaoCorrigida));
    }
}
