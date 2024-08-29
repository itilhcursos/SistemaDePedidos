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

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;;

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
            "Produto teste",
            14.00,
            BigDecimal.valueOf(25.90),
            true);
        produtoRepository.save(produto);
    }

    @Test
    @DisplayName("teste de id existente ")
    void TesteGetIdExistente() throws Exception{
        setUpProduto();
        mockMvc.perform(get("/produto/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("Produto teste")));
    }

    @Test
    @DisplayName("teste de id inexitente")
    void TesteGetIdInexistente() throws Exception{
        mockMvc.perform(get("/produto/9999999")).andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof IdInexistenteException));
    }

    @Test
    @DisplayName("teste de path errado")
    void TesteGetPathErrado() throws Exception{
        mockMvc.perform(get("/produtounkjxpto")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("teste de post de novo Produto")
    void TestePostProduto() throws Exception{
        mockMvc.perform( 
            post("/produto")
            .contentType("application/json")
            .content("{\r\n" + //
                    "  \"id\": 0,\r\n" + //
                    "  \"descricao\": \"Produto teste\",\r\n" + //
                    "  \"quantidadeEstoque\": 10.00,\r\n" + //
                    "  \"precoUnidadeAtual\": 12.99,\r\n" + //
                    "  \"ativo\": \"true\"\r\n" + //
                                "}")        
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("teste de put de novo Produto")
    void TestePutProduto() throws Exception{
        setUpProduto();
        mockMvc.perform( 
            put("/produto/1")
            .contentType("application/json")
            .content("{\r\n" + //
                    "  \"id\": 1,\r\n" + //
                    "  \"descricao\": \"Produto teste Alterado\",\r\n" + //
                    "  \"quantidadeEstoque\": 10.00,\r\n" + //
                    "  \"precoUnidadeAtual\": 12.99,\r\n" + //
                    "  \"ativo\": \"true\"\r\n" + //
                                "}")        
        ).andExpect(status().isOk()
        ).andExpect(content().string(containsString("Produto teste Alterado")));
    }

    @Test
    @DisplayName(" Teste do delete")
    void testeDeleteProduto() throws Exception{
        setUpProduto();
        mockMvc.perform( delete("/produto/1")
        ).andExpect(status().isOk()
        ).andExpect(content().string(containsString("Excluído")));
    }






}
