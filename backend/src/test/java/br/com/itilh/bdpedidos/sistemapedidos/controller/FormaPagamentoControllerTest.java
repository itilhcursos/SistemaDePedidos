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
import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FormaPagamentoControllerTest {


    @Autowired
    MockMvc  mockMvc;

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;
    
    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @DisplayName("alterar forma Pagamento")
    void testAlterarFormaPagamento()throws Exception {
        setupFormaPagamento();
        mockMvc.perform(put("/forma-pagamento/1")
        .contentType("application/json")
        .content( "{\r\n" + //
        "  \"id\": 1, \r\n" + //
        " \"descricao\": \"Forma Pagamento alterado\",\r\n" + //
        " \"ativo\": \"true\",\r\n" + //  
        "}")  
        ).andExpect(status().isOk())
        .andExpect(content().string(containsString("Forma Pagamento alterado")));
    }
    

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @DisplayName("teste de criar forma-pagamento")
    void testCriarFormaPagamento()throws Exception {
        setupFormaPagamento();
        mockMvc.perform(post("/forma-pagamento/1")
        .contentType("application/json")
        .content( "{\r\n" + //
        "  \"id\": 1, \r\n" + //
        " \"descricao\": \"Forma Pagamento teste\",\r\n" + //
        " \"ativo\": \"true\",\r\n" + //  
        "}")  
        ).andExpect(status().isOk());
        
    }
    

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @DisplayName("teste de delete do forma-pagamrento")
    void testDeletePorId() throws Exception {
        setupFormaPagamento();
        mockMvc.perform(delete("/forma-pagamento/1"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Excluído")));
    }
    

    @Test
    @DisplayName("Teste do path /formas-pagamento por id")
    void testGetPorId() throws Exception{
        setupFormaPagamento();
        mockMvc.perform(get("/forma-pagamento/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("1")));
    }


    @Test
    @DisplayName("Teste do id existente")
    void testIdExistente() throws Exception {
        setupFormaPagamento();
        mockMvc.perform(get("/forma-pagamento/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("Forma Pagamento teste")));

    }
    @Test
    @DisplayName("Teste do path /formas-pagamento")
    void testGetTodos() throws Exception {
        setupFormaPagamento();
        mockMvc.perform(get("/formas-pagamento")).andExpect(status().isOk())
        .andExpect(content().string(containsString("Forma Pagamento teste")));
    }


void setupFormaPagamento(){
    FormaPagamento formaPagamento = new FormaPagamento(BigInteger.ONE, "Forma Pagamento teste", true);
    formaPagamentoRepository.save(formaPagamento);
}



    @Test
    @DisplayName("Teste do path inexistente")
    void TesteGetPathInexistente() throws Exception{
        setupFormaPagamento();
        mockMvc.perform(get("/forma-pagamento")).andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("Teste do path errado")
    void TesteGetPathErrado() throws Exception{
        setupFormaPagamento();
        mockMvc.perform(get("/forma-pagamentopok")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Teste do id inexistente")
    void TesteGetIdInexistente() throws Exception{
        setupFormaPagamento();
        mockMvc.perform(get("/forma-pagamento/1811815481")).andExpect(status().isBadRequest())
       .andExpect(result -> assertTrue(result.getResolvedException()instanceof IdInexistenteException));
    }
}