package br.com.itilh.bdpedidos.sistemapedidos.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigInteger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoEstoqueNegativoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoPrecoNegativoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;

public class ProdutoControllerTest {
    @Autowired
    private MockMvc mockMvc;

  @Autowired
    private ProdutoRepository produtoRepository;

    @BeforeEach
    void setUp() {
        setupProduto();
    }

    @AfterEach
    void tearDown() {
        produtoRepository.deleteAll();
    }

      void setupProduto() {
        Produto produto = new Produto(
            BigInteger.ONE, "Produto teste", true);
                produtoRepository.save(produto);
    }

    @Test
    @DisplayName("Teste de GET para listar todos os produtos")
    void testGetTodos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].descricao").value("Produto Teste"));
    }

    @Test
    @DisplayName("Teste de GET por ID existente")
    void testGetPorIdExistente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/produto/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Produto Teste"));
    }

    @Test
    @DisplayName("Teste de GET por ID inexistente")
    void testGetPorIdInexistente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/produto/999999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Teste de POST para criar um novo produto")
    void testPostProduto() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/produto")
                        .contentType("application_json")
                        .content("{\"id\": 2, \"descricao\": \"Produto novo\", \"ativo\": true}"))
                        .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Teste de POST para criar produto duplicado")
    void testPostProdutoDuplicado() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/produto")
                .contentType("application/json")
                .content("{\"id\": 1, \"descricao\": \"Produto teste duplicado\", \"ativo\": true}"))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof ProdutoDuplicadoException));
    }

    @Test
    @DisplayName("Teste de POST para criar produto com estoque negativo")
    void testPostProdutoComEstoqueNegativo() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/produto")
                .contentType("application/json")
                .content("{\"id\": 1, \"descricao\": \"Produto teste estoque negativo\", \"ativo\": true}"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof ProdutoEstoqueNegativoException));
    }

    @Test
    @DisplayName("Teste de POST para criar produto com preço negativo")
    void testPostProdutoComPrecoNegativo() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/produto")
                .contentType("application/json")
                .content("{\"id\": 1, \"descricao\": \"Produto teste preco negativo\", \"ativo\": true}"))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof ProdutoPrecoNegativoException));
    }

    @Test
    @DisplayName("Teste de PUT para atualizar um produto existente")
    void testPutProduto() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/produto/1")
                .contentType("application/json")
                .content("{\"id\": 1, \"descricao\": \"Produto teste alterado\", \"ativo\": true}"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Produto Alterado"));
    }

    @Test
    @DisplayName("Teste de DELETE para excluir um produto")
    void testDeleteProduto() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/produto/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Excluído"));
    }
}
