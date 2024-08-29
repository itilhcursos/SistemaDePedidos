package br.com.itilh.bdpedidos.sistemapedidos.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.math.BigDecimal;
import java.math.BigInteger;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ProdutoControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProdutoRepository produtoRepository;

    @Test
    @DisplayName("teste do path /produtos")
      void testGetProdutos() throws Exception {
        mockMvc.perform(get("/produtos")).andExpect(status().isOk())
        .andExpect(content().string(containsString("totalElements")));
    }

    @Test
    @DisplayName("teste de path inexistente")
      void TesteGetPathInexistente() throws Exception{
        mockMvc.perform(get("/produto")).andExpect(status().isMethodNotAllowed());
    }

      void setUpProduto(){
        Produto produto = new Produto(
            BigInteger.ONE,
            "teste produto",
            1.0,
            BigDecimal.valueOf(10),
            true);
        produtoRepository.save(produto);
    }

    @Test
    @DisplayName("teste de id existente ")
      void TesteGetIdExistente() throws Exception{
        setUpProduto();
        mockMvc.perform(get("/produto/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("teste produto")));
    }

    @Test
    @DisplayName("teste de id inexistente")
      void TesteGetIdInexistente() throws Exception{
     mockMvc.perform(get("/produto/9999999")).andExpect(status().isBadRequest())
          .andExpect(result -> assertTrue(result.getResolvedException() instanceof IdInexistenteException));
    }

    @Test
    @DisplayName("teste de path errado")
      void TesteGetPathErrado() throws Exception{
        mockMvc.perform(get("/produtoerrado")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("teste post de novo produto")
      void TestePostFormaPagamento() throws Exception{
        mockMvc.perform( 
            post("/produto")
            .contentType("application/json")
            .content("{\n" +
                    "  \"id\": 2,\n" +
                    "  \"descricao\": \"teste produto\",\n" +
                    "  \"quantidadeEstoque\": 2,\n" +
                    "  \"precoUnidadeAtual\": 10,\n" +
                    "  \"ativo\": true\n" +
                    "}")        
        ).andExpect(status().isOk());
    }


    @Test
    @DisplayName("teste put de novo produto")
      void TestePutProduto() throws Exception {
        setUpProduto();
        mockMvc.perform(
                put("/produto/1")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"id\": 1,\n" +
                                "  \"descricao\": \"teste produto alterado\",\n" +
                                "  \"quantidadeEstoque\": 2,\n" +
                                "  \"precoUnidadeAtual\": 10,\n" +
                                "  \"ativo\": true\n" +
                                "}") )
                .andExpect(status().isOk()).andExpect(content().string(containsString("teste produto alterado")));
    }
    
    @Test
    @DisplayName("Teste do delete")
      void testeDeleteProduto() throws Exception {
        setUpProduto();
        mockMvc.perform(delete("/produto/1")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Excluído")));
    }
    


}
