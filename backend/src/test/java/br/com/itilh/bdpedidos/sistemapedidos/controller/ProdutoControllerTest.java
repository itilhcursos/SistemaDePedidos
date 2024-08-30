package br.com.itilh.bdpedidos.sistemapedidos.controller;

import java.math.BigInteger;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;

@SpringBootTest
@AutoConfigureMockMvc

public class ProdutoControllerTest {
    
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProdutoRepository produtoRepository;


    @Test
    @DisplayName("teste path /produtos")
    void testGetTodosProdutos() throws Exception {
        
        mockMvc.perform(get("/produtos")).andExpect(status().isOk())
        .andExpect(content().string(containsString("totalElements")));

    }

    @Test
    @DisplayName("teste de path inexistente")
    void TesteGetPathInexistente() throws Exception{
        mockMvc.perform(get("/produtos")).andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("teste de id existente ")
    void TesteGetIdExistente() throws Exception{
        setupProduto();
        mockMvc.perform(get("/municipio/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("Municipio teste")));
    }

     @Test
    @DisplayName("Teste de id inexistente")
    void testGetIdInexistente() throws Exception {

       mockMvc.perform(get("/produto/9999999")).andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof IdInexistenteException));
    }
    
    @Test
    @DisplayName("Teste do path errado")
    void testGetPathErrado() throws Exception {

        mockMvc.perform(get("/produtoxpto")).andExpect(status().isNotFound());

    }

    Produto produto = new Produto(
      BigInteger.ONE, "Produto teste", true );
      ProdutoRepository.save(produto);

    @Test
    @DisplayName("teste de post de novo Produto")
    void testCriarProduto() throws Exception {
        mockMvc.perform(post("/produto").contentType("application/json").content(
            "{\r\n" + //
                                "  \"id\": null,\r\n" + //
                                "  \"descricao\": \"Produto teste\",\r\n" + //
                                "  \"quantidadeEstoque\": 1,\r\n" + //
                                "  \"precoUnidadeAtual\": 200,\r\n" + //
                                "  \"ativo\": false\r\n" + //
                                "}"
        ))
        .andExpect(status().isOk());

        @DisplayName ("teste de alterar um Produto")
    void testAlterarProduto() throws Exception {

        setUpProduto();
        mockMvc.perform(put("/produto/1").contentType("application/json").content(
            "{\r\n" + //
                        "  \"id\": 1,\r\n" + //
                        "  \"descricao\": \"Produto Alterado\",\r\n" + //
                        "  \"quantidadeEstoque\": 1,\r\n" + //
                        "  \"precoUnidadeAtual\": 250,\r\n" + //
                        "  \"ativo\": false\r\n" + //
                        "}"  
        ))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Produto Alterado")));

    }

    @DisplayName ("teste de deletar Produto")
    void testDeleteProduto() throws Exception {

        setUpProduto();
        
        mockMvc.perform(delete("/produto/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("Excluído")));

    }



}
