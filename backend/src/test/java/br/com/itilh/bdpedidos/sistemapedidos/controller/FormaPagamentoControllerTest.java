package br.com.itilh.bdpedidos.sistemapedidos.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@SpringBootTest
@AutoConfigureMockMvc
public class FormaPagamentoControllerTest {


    @Autowired
    MockMvc  mockMvc;

    @Test
    
    void testAlterarFormaPagamento()throws Exception {

    }

    @Test
    @DisplayName("teste de criar forma-pagamento")
    void testCriarFormaPagamento()throws Exception {
        mockMvc.perform(post("/produto")
        .contentType("application/json")
        .content( "{\r\n" + //
        "  \"id\": 1, \r\n" + //
        " \"descricao\": \"formas-pagamento teste\",\r\n" + //
        " \"ativo\": \"true\",\r\n" + //  
        "}")  
        ).andExpect(status().isOk());
    }

    @Test
    void testDeletePorId() {

    }

    @Test
    void testGetPorId() {

    }


    @Test
    @DisplayName("Teste do id existente")
    void testIdExistente() throws Exception {
        mockMvc.perform(get("/forma-pagamento/3")).andExpect(status().isOk())
        .andExpect(content().string(containsString("PIX")));

    }
    @Test
    @DisplayName("Teste do path /formas-pagamento")
    void testGetTodos() throws Exception {
        mockMvc.perform(get("/formas-pagamento")).andExpect(status().isOk())
        .andExpect(content().string(containsString("totalElements")));
    }

    @Test
    @DisplayName("Teste do path inexistente")
    void TesteGetPathInexistente() throws Exception{
        mockMvc.perform(get("/forma-pagamento")).andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("Teste do path errado")
    void TesteGetPathErrado() throws Exception{
        mockMvc.perform(get("/forma-pagamentopok")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Teste do id inexistente")
    void TesteGetIdInexistente() throws Exception{
        mockMvc.perform(get("/forma-pagamento/1811815481")).andExpect(status().isBadRequest())
       .andExpect(result -> assertTrue(result.getResolvedException()instanceof IdInexistenteException));
    }
}
