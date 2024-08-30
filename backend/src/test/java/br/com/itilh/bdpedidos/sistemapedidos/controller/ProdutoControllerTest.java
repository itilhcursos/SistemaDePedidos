package br.com.itilh.bdpedidos.sistemapedidos.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

     void setUpProduto(){
        Produto produto = new Produto(BigInteger.ONE, "produto teste", null, null, null);
        produtoRepository.save(produto);
    }


    // =========================================================================
    @Test
    @DisplayName("testando o retorno da busca da lista de produto pelo endpoint /produto")
    void testGetProduto() throws Exception {

        mockMvc.perform(get("/produtos")).andExpect(status().isOk())
                .andExpect(content().string(containsString("totalElements")));

    }

    // =========================================================================
    @Test
    @DisplayName("testando path inexistente")
    void testPathInexistente() throws Exception {
        mockMvc.perform(get("/produto")).andExpect(status().isMethodNotAllowed());
    }

    // =========================================================================
    @Test
    @DisplayName("teste de path errado")
    void TesteGetPathErrado() throws Exception {
        mockMvc.perform(get("/produto-patherrado")).andExpect(status().isNotFound());
    }

    // =========================================================================
    @Test
    @DisplayName("teste do Post para criar produto")
    void testCriarProduto() throws Exception {

        mockMvc.perform(
                post("/produto")
                        .contentType("application/json")
                        .content("{\r\n" + //
                                "  \"id\": 0,\r\n" + //
                                "  \"descricao\": \"string\",\r\n" + //
                                "  \"quantidadeEstoque\": 0,\r\n" + //
                                "  \"precoUnidadeAtual\": 0,\r\n" + //
                                "  \"ativo\": true\r\n" + //
                                "}"))
                .andExpect(status().isOk());

    }
    //========================================================================================
    @Test
    @DisplayName("teste de put de novo Municipio")
    void TestePutProduto() throws Exception{
        setUpProduto();
        mockMvc.perform( 
            put("/produto/1")
            .contentType("application/json")
            .content("{\r\n" + //
                                "  \"id\": 0,\r\n" + //
                                "  \"descricao\": \"string\",\r\n" + //
                                "  \"quantidadeEstoque\": 0,\r\n" + //
                                "  \"precoUnidadeAtual\": 0,\r\n" + //
                                "  \"ativo\": true\r\n" + //
                                "}")        
        ).andExpect(status().isOk()
        ).andExpect(content().string(containsString("produto teste alterado")));
    }

    
   


}
