package br.com.itilh.bdpedidos.sistemapedidos.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.math.BigInteger;

import static org.hamcrest.Matchers.containsString;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;
import jdk.jfr.ContentType;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProdutoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProdutoRepository produtoRepository;



    void setUpProduto(){
        Produto produto = new Produto(
            BigInteger.ONE,
            "Produto test",
            100,
            15.22,
            true
        
        );
        produtoRepository.save(produto);
    }
    


    
    @Test
    @DisplayName("retorno do produto por id Existente")
    void testGetProdutoPorId() throws Exception {
        setUpProduto();
        mockMvc.perform(get("/produto/1")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("retorno do produto por id inexistente")
    void testGetProdutoPorIdInexistente() throws Exception {
        mockMvc.perform(get("/produto/9999")).andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof IdInexistenteException));
    }

    @Test
    @DisplayName("retorno de todos os produtos pelo path")
    void testGetTodosProdutos() throws Exception {
        mockMvc.perform(get("/produtos")).andExpect(status().isOk());
    }

    @Test
    @DisplayName("test de path errado")
    void testGetTodosProdutosNaoEncontrado() throws Exception {
        mockMvc.perform(get("/produto")).andExpect(status().isMethodNotAllowed());
    }
    
    
    @Test
    void testCriarProduto() throws Exception{
        
        mockMvc.perform(
            post("/produto")
            .contentType("application/json")
            .content("{\r\n" + //
                                "  \"id\": 0,\r\n" + //
                                "  \"descricao\": \"produto test\",\r\n" + //
                                "  \"quantidadeEstoque\": 45,\r\n" + //
                                "  \"precoUnidadeAtual\": 15.22,\r\n" + //
                                "  \"ativo\": true\r\n" + //
                                "}")
        ) .andExpect(status().isOk());
    }

    @Test
    @DisplayName("test de alteração de Produto ")
    void testAlterarProduto() throws Exception{
        setUpProduto();
        mockMvc.perform(
            put("/produto/1")
            .contentType("application/json")
            .content("{\r\n" + //
                                "  \"id\": 1,\r\n" + //
                                "  \"descricao\": \"Produto test alterado\",\r\n" + //
                                "  \"quantidadeEstoque\": 45,\r\n" + //
                                "  \"precoUnidadeAtual\": 15.22,\r\n" + //
                                "  \"ativo\": true\r\n" + //
                                "}")
        ).andExpect(status().isOk()
        ).andExpect(content().string(containsString("Produto test alterado")));
    }

    @Test
    void testDeleteProduto() throws Exception{
        mockMvc.perform(delete("/produto/1"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Excluido")));
    }

}
