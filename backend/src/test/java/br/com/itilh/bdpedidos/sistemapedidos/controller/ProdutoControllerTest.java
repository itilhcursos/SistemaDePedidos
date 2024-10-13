package br.com.itilh.bdpedidos.sistemapedidos.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")

public class ProdutoControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProdutoRepository produtoRepository;

    void setupProduto() {
        Produto produto = new Produto(
                BigInteger.ONE,
                "produto teste",
                10.00,
                BigDecimal.valueOf(56.99),
                true);
        produtoRepository.save(produto);
    }

    @Test
    @DisplayName("Teste Altera Produto")
    void testAlterarProduto() throws Exception {
        setupProduto();
        mockMvc.perform(put("/produto/1")
                .contentType("application/json")
                .content("{\r\n" +
                        " \"id\": 1," +
                        " \"descricao\": \"Produto teste Alteado\",\r\n" +
                        " \"quantidadeEstoque\": \"100\",\r\n" +
                        " \"precoUnidadeAtual\": \"99.99\",\r\n" +
                        " \"ativo\": true\r\n" +
                        "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Produto teste Alteado")));

    }

    @Test
    @DisplayName("Criar Novo Produto")
    void testCriarProduto() throws Exception {
        mockMvc.perform(post("/produto")
                .contentType("application/json")
                .content("{\r\n" +
                        " \"id\": null," +
                        " \"descricao\": \"Teste produto\",\r\n" +
                        " \"quantidadeEstoque\": \"100\",\r\n" +
                        " \"precoUnidadeAtual\": \"99.99\",\r\n" +
                        " \"ativo\": true\r\n" +
                        "}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Teste delete produto")
    void testDeleteProdutoPorId() throws Exception {
        setupProduto();
        mockMvc.perform(delete("/produto/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Excluído")));

    }

    @Test
    @DisplayName("Teste Get produto por ID")
    void testGetProdutoPorId() throws Exception {
        setupProduto();
        mockMvc.perform(get("/produto/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("produto teste")));

    }

    @Test
    @DisplayName("Teste de busca produto")
    void testGetTodosProdutos() throws Exception {
        mockMvc.perform(get("/produtos"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("totalElements")));

    }

    @Test
    @DisplayName("Teste de id existente")
    void testGetIdExistente() throws Exception {
        setupProduto();
        mockMvc.perform(get("/produto/1")).andExpect(status().isOk())
                .andExpect(content().string(containsString("produto teste")));
    }

}
