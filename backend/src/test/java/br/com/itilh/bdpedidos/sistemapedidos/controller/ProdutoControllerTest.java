package br.com.itilh.bdpedidos.sistemapedidos.controller;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("Test")
public class ProdutoControllerTest {
    
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ProdutoRepository produtoRepository;

    @Test
    @DisplayName("Test do Path /Produtos")
    void testGetPathProdutos() throws Exception{
         mockMvc.perform(get("/produtos")).andExpect(status().isOk())
         .andExpect(content().string(containsString("totalElements")));
    }

    @Test
    @DisplayName("Teste de Path Inexistente")
    void TesteGetPathInexistente() throws Exception{
         mockMvc.perform(get("/produto")).andExpect(status().isMethodNotAllowed());
    }

    void setUpProduto(){
        Produto produto = new Produto(
        BigInteger.ONE, "Produto teste", 08.00, BigDecimal.valueOf(800.00),true);
        produtoRepository.save(produto);
    }

    @Test
    @DisplayName("Teste de ID Existente ")
    void TesteGetIdExistente() throws Exception{
         setUpProduto();
         mockMvc.perform(get("/produto/1")).andExpect(status().isOk())
         .andExpect(content().string(containsString("Produto Teste")));
    }

    @Test
    @DisplayName("Teste de ID Inexitente")
    void TesteGetIdInexistente() throws Exception{
         mockMvc.perform(get("/produto/9999999")).andExpect(status().isBadRequest())
         .andExpect(result -> assertTrue(
          result.getResolvedException()
          instanceof IdInexistenteException));
    }

    @Test
    @DisplayName("Teste de Path Errado")
    void TesteGetPathErrado() throws Exception{
         mockMvc.perform(get("/produtoErrado")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Teste de Post de Novo Produto")
    void TestePostNovoProduto() throws Exception{
          mockMvc.perform( 
          post("/produto")
         .contentType("application/json")
         .content("{\r\n" +
         "\"id\": 0,\r\n" + 
         "\"descricao\": \"Produto teste\",\r\n" +
         "\"quantidadeEstoque\": 0,\r\n" +
         "\"precoUnidadeAtual\": 0,\r\n" +
         "\"ativo\": true\r\n" +
         "}")        
         ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("teste de put de novo Produto")
    void  TestePutNovoMunicipio() throws Exception{
          setUpProduto();
          mockMvc.perform( 
          put("/produto/1")
          .contentType("application/json")
          .content("{\r\n" +
          "\"id\": 1,\r\n" +
          "\"descricao\": \"Produto teste Alterado\",\r\n" + //
          "\"quantidadeEstoque\": 0,\r\n" + //
          "\"precoUnidadeAtual\": 0,\r\n" + //
          "\"ativo\": true\r\n" + //
          "}")       
           ).andExpect(status().isOk()
           ).andExpect(content().string(
           containsString("Produto Teste Alterado")));
    }

    @Test
    @DisplayName("Teste do Delete")
    void testeDeleteProduto() throws Exception{
         setUpProduto();
         mockMvc.perform(delete("/produto/1")
         ).andExpect(status().isOk()
         ).andExpect(content().string(containsString("Excluído")));
    }
}
