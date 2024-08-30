package br.com.itilh.bdpedidos.sistemapedidos.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("Test")
public class FormaPagamentoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    @Test
    @DisplayName("Teste do Path /FormasPagamento")
    void testGetFormasPagamento() throws Exception{
         mockMvc.perform(get("/formas-pagamento")).andExpect(status().isOk())
         .andExpect(content().string(containsString("totalElements")));
    }

    @Test
    @DisplayName("Teste de Path Inexistente")
    void TesteGetPathInexistente() throws Exception{
         mockMvc.perform(get("/forma-pagamento")).andExpect(status().isMethodNotAllowed());
    }

    void setUpFormaPagamento(){
        FormaPagamento formaPagamento = new FormaPagamento(
        BigInteger.ONE,
        "PIX",
        true);
        formaPagamentoRepository.save(formaPagamento);
    }
    @Test
    @DisplayName("Teste de Id Existente ")
    void TesteGetIdExistente() throws Exception{
        setUpFormaPagamento();
        mockMvc.perform(get("/forma-pagamento/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("Forma Pagamento TSeste")));
    }

    @Test
    @DisplayName("Teste de ID Inexistente")
    void TesteGetIdInexistente() throws Exception{
         mockMvc.perform(get("/formaPagamento/2222222")).andExpect(status().isBadRequest())
         .andExpect(result -> assertTrue(result.getResolvedException() instanceof IdInexistenteException));
    }

    @Test
    @DisplayName("Teste de Path Errado")
    void TesteGetPathErrado() throws Exception{
        mockMvc.perform(get("/formaPpagamentoerrado")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Teste Post de Nova formaPagamento")
    void TestePostFormaPagamento() throws Exception{
         mockMvc.perform( 
         post("/forma-pagamento")
         .contentType("application/json")
         .content("{\n" +
         "\"id\": 1,\n" +
         "\"descricao\": \"Cheque\",\n" +
         "\"ativo\": true\n" +
         "}"
         )
         ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("teste put de nova forma pagamento")
    void TestePutFormaPagamento() throws Exception {
        setUpFormaPagamento();
        mockMvc.perform(
        put("/formaPagamento/1")
        .contentType("application/json")
        .content("{\r\n" + //
        "  \"id\": 1,\r\n" + //
        "  \"descricao\": \"forma de pagamento teste alterado\",\r\n" + //
        "  \"ativo\": true\r\n" + //
        "}"
        )
        )
        .andExpect(status().isOk()).andExpect(
        content().string(containsString("Forma de Pagamento Teste Alterado")));
    }

    @Test
    @DisplayName("Teste Delete")
    void testeDeleteFormaPagamento() throws Exception {
        setUpFormaPagamento();
        mockMvc.perform(delete("/formaPagamento/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("Excluído")));
    }
}
