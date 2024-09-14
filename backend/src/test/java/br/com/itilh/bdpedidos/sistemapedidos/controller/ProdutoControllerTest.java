package br.com.itilh.bdpedidos.sistemapedidos.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProdutoControllerTest {


    @Autowired
    MockMvc  mockMvc;

    @Autowired
    ProdutoRepository produtoRepository;


    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
@DisplayName("alterar produto")
    void testAlterarProduto() throws Exception{
        setupProduto();
        mockMvc.perform(put("/produto/1")
        .contentType("application/json")
        .content("{\r\n" + //
        "  \"id\": 1, \r\n" + //
        " \"descricao\": \"produto teste alterado\",\r\n" + //
        " \"quantidadeEstoque\": \"1\",\r\n" + //
        " \"precoUnidadeAtual\": \"1\",\r\n" + //
        " \"ativo\": \"true\",\r\n" + //  
        "}")
        ).andExpect(status().isOk())
        .andExpect(content().string(containsString("produto teste alterado")));
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @DisplayName("teste de criar produto")
    void testCriarProduto()throws Exception {
        setupProduto();
        mockMvc.perform(post("/produto/1")
        .contentType("application/json")
        .content("{\r\n" + //
        "  \"id\": 1, \r\n" + //
        " \"descricao\": \"produto teste\",\r\n" + //
        " \"quantidadeEstoque\": \"1\",\r\n" + //
        " \"precoUnidadeAtual\": \"1\",\r\n" + //
        " \"ativo\": \"true\",\r\n" + //  
        "}")
        ).andExpect(status().isOk());
    }
    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @DisplayName("teste de delete do produto")
    void testDeleteProduto() throws Exception {
        setupProduto();
        mockMvc.perform(delete("/produto/1")
        ).andExpect(status().isOk())
        .andExpect(content().string(containsString("Excluído")));
    }
    

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @DisplayName("teste de buscar produto por id")
    void testGetProdutoPorId()throws Exception {
        setupProduto();
        mockMvc.perform(get("/produto/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("1")));
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @DisplayName("Teste do path /produtos")
    void testGetTodosProdutos() throws Exception {
        setupProduto();
        mockMvc.perform(get("/produtos")).andExpect(status().isOk())
        .andExpect(content().string(containsString("produto teste")));
    }


    void setupProduto(){
        Produto produto = new Produto(BigInteger.ONE, "produto teste",Double.valueOf(1), BigDecimal.valueOf(1), true);
        produtoRepository.save(produto);
    }



    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @DisplayName("Teste do path inexistente")
    void TesteGetPathInexistente() throws Exception{
        setupProduto();
        mockMvc.perform(get("/produto")).andExpect(status().isMethodNotAllowed());

    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @DisplayName("Teste do path errado")
    void TesteGetPathErrado() throws Exception{
        setupProduto();
        mockMvc.perform(get("/produtossss")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Teste do id existente")
    void testIdExistente() throws Exception {
        setupProduto();
        mockMvc.perform(get("/produto/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("1")));
    }


    @Test
    @DisplayName("Teste do id inexistente")
    void TesteGetIdInexistente() throws Exception{
        setupProduto();
        mockMvc.perform(get("/produto/1811818891")).andExpect(status().isBadRequest())
         .andExpect(result -> assertTrue(result.getResolvedException()instanceof IdInexistenteException));

    }
}