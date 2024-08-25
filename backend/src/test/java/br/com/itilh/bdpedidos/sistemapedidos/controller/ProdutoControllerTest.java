package br.com.itilh.bdpedidos.sistemapedidos.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.
MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.math.BigInteger;
import java.math.BigDecimal;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;

@SpringBootTest
@AutoConfigureMockMvc // só para controller
@ActiveProfiles("test") // para pegar as properties de teste

public class ProdutoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProdutoRepository produtoRepository;

    
    @Test
    @DisplayName("teste do path /Produtos")
    void testGetProdutos() throws Exception {
        mockMvc.perform(get("/produtos")).andExpect(status().isOk())
        .andExpect(content().string(containsString("totalElements")));

    }


    @Test
    @DisplayName("teste de path inexistente")
    void TesteGetPathInexistente() throws Exception{
        mockMvc.perform(get("/produto")).andExpect(status().isMethodNotAllowed());
    }


    // criação das entidades necessárias parta os teste de existência de produto
    void setupProduto() {
        Produto produto = new Produto(
                BigInteger.ONE,
                "Produto teste",
                0.0,
                BigDecimal.valueOf(2.90),
                true);
        produtoRepository.save(produto);
    }


    
    @Test
    @DisplayName("teste de id existente ")
    void TesteGetIdExistente() throws Exception{
        setupProduto();
        mockMvc.perform(get("/produto/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("Produto teste")));
    }



    @Test
    @DisplayName("teste de id inexitente")
    void TesteGetIdInexistente() throws Exception{
        mockMvc.perform(get("/produto/999340557")).andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof IdInexistenteException));
    }




    @Test
    @DisplayName("teste de path errado")
    void TesteGetPathErrado() throws Exception{
        mockMvc.perform(get("/produtojrferriabcd")).andExpect(status().isNotFound());
    }



    @Test
    @DisplayName("teste de post de novo Produto")
    void TestePostProduto() throws Exception{
        setupProduto();
        mockMvc.perform( 
            post("/produto")
            .contentType("application/json")
            .content("{\r\n" + //
                    "  \"id\": 0,\r\n" + //
                    "  \"descricao\": \"Produto teste\",\r\n" + //
                    "  \"quantidadeEstoque\": 0,\r\n" + //
                    "  \"precoUnidadeAtual\": 0.0,\r\n" + //
                    "  \"ativo\": \"true\"\r\n" + //
                                "}")        
        ).andExpect(status().isOk());
    }

    
    @Test
    @DisplayName("teste de put de novo Produto")
    void TestePutProduto() throws Exception{
        setupProduto();
        mockMvc.perform( 
            put("/produto/1")
            .contentType("application/json")
            .content("{\r\n" + //
                    "  \"id\": 1,\r\n" + //
                    "  \"descricao\": \"Produto teste Alterado\",\r\n" + //
                    "  \"quantidadeEstoque\": 10,\r\n" + //
                    "  \"precoUnidadeAtual\": 0.99,\r\n" + //
                    "  \"ativo\": \" true\"\r\n" + //
                                "}")        
        ).andExpect(status().isOk()
        ).andExpect(content().string(containsString("Produto teste Alterado")));
    }
  


    @Test
    @DisplayName(" Teste do delete")
    void testeDeleteProduto() throws Exception{
        setupProduto();
        mockMvc.perform( delete("/produto/1")
        ).andExpect(status().isOk()
        ).andExpect(content().string(containsString("Excluído")));
    }
    }

