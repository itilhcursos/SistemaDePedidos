package br.com.itilh.bdpedidos.sistemapedidos.controller;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import br.com.itilh.bdpedidos.sistemapedidos.exception.FormaPagamentoDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")

public class FormaPagamentoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;

    @BeforeEach
    void setUp() {
        setupFormaPagamento();
    }

    @AfterEach
    void tearDown() {
        formaPagamentoRepository.deleteAll();
    }

    void setupFormaPagamento() {
        FormaPagamento formaPagamento = new FormaPagamento(
                BigInteger.ONE, "Pagamento teste", true);
        formaPagamentoRepository.save(formaPagamento);
    }

    @Test
    @DisplayName("Teste de GET para listar todas as formas de pagamento")
    void testGetTodos() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/formas-pagamento"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Pagamento teste")));
    }

    @Test
    @DisplayName("Teste de GET por ID existente")
    void testGetPorIdExistente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/forma-pagamento/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("Pagamento teste"));
    }

    @Test
    @DisplayName("Teste de GET por ID inexistente")
    void testGetPorIdInexistente() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/forma-pagamento/999999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DisplayName("Teste de POST para criar uma nova forma de pagamento")
    void testPostFormaPagamento() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/forma-pagamento")
                        .contentType("application/json")
                        .content("{\"id\": 2, \"nome\": \"Pagamento novo\", \"ativo\": true}"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Teste de POST para criar forma de pagamento duplicada")
    void testPostFormaPagamentoDuplicado() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/forma-pagamento")
                        .contentType("application/json")
                        .content("{\"id\": 1, \"nome\": \"Pagamento teste\", \"ativo\": true}"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(result -> assertTrue(
                        result.getResolvedException() instanceof FormaPagamentoDuplicadoException));
    }

    @Test
    @DisplayName("Teste de PUT para atualizar uma forma de pagamento existente")
    void testPutFormaPagamento() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.put("/forma-pagamento/1")
                        .contentType("application/json")
                        .content("{\"id\": 1, \"nome\": \"Pagamento teste Alterado\", \"ativo\": true}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Pagamento teste Alterado")));
    }

    @Test
    @DisplayName("Teste de DELETE para excluir uma forma de pagamento")
    void testDeleteFormaPagamento() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/forma-pagamento/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("Excluído")));
    }

}
