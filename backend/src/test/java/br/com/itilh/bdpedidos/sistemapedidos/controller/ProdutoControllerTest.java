package br.com.itilh.bdpedidos.sistemapedidos.controller;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
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
        Produto p1 = new Produto(
            BigInteger.ONE,
            "Produto",
            Double.valueOf(40),
            BigDecimal.TEN,
            true,
            "https://th.bing.com/th?id=ORMS.0d66395705a0ee830d3867211377526e&pid=Wdp&w=612&h=304&qlt=90&c=1&rs=1&dpr=1&p=0"
        );
        produtoRepository.save(p1);
    }

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

    @Test
    @DisplayName("teste de id existente ")
    void TesteGetIdExistente() throws Exception{
        setUpProduto();
        mockMvc.perform(get("/produto/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("Produto")));
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
        mockMvc.perform(get("/produtoxpto")).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username="admin", roles={"USER","ADMIN"})
    @DisplayName("teste de post de novo Produto")
    void TestePostProduto() throws Exception{
        setUpProduto();
        mockMvc.perform( 
            post("/produto")
            .contentType("application/json")
            .content("{\r\n" + //
                                "  \"id\": 0,\r\n" + //
                                "  \"descricao\": \"Produto teste\",\r\n" + //
                                "  \"quantidadeEstoque\": 100,\r\n" + //
                                "  \"precoUnidadeAtual\": 20,\r\n" + //
                                "  \"ativo\": false,\r\n" + //
                                "  \"urlImagem\": \"string\"\r\n" + //
                                "}")        
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin", roles={"USER","ADMIN"})
    @DisplayName("teste de put de novo Produto")
    void TestePutProduto() throws Exception{
        setUpProduto();
        mockMvc.perform( 
            put("/produto/1")
            .contentType("application/json")
            .content("{\r\n" + //
                                "  \"id\": 1,\r\n" + //
                                "  \"descricao\": \"Produto teste alterado\",\r\n" + //
                                "  \"quantidadeEstoque\": 100,\r\n" + //
                                "  \"precoUnidadeAtual\": 20,\r\n" + //
                                "  \"ativo\": true,\r\n" + //
                                "  \"urlImagem\": \"string\"\r\n" + //
                                "}")        
        ).andExpect(status().isOk()
        ).andExpect(content().string(containsString("Produto teste alterado")));
    }

    @Test
    @WithMockUser(username="admin", roles={"USER","ADMIN"})
    @DisplayName(" Teste do delete")
    void testeDeleteProduto() throws Exception{
        setUpProduto();
        mockMvc.perform( delete("/produto/1")
        ).andExpect(status().isOk()
        ).andExpect(content().string(containsString("Excluído")));
    }

}
