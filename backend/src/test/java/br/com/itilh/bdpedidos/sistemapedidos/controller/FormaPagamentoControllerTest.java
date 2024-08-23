package br.com.itilh.bdpedidos.sistemapedidos.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FormaPagamentoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    @Test
    @DisplayName("Teste do path /formas-pagamento")
    void testGetFormasPagamento() throws Exception {
        mockMvc.perform(get("/formas-pagamento")).andExpect(status().isOk())
                .andExpect(content().string(containsString("totalElements")));
    }

    @Test
    @DisplayName("Teste de path inexistente")
    void TesteGetPathInexistente() throws Exception {
        mockMvc.perform(get("/forma-pagamento")).andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("Teste de id existente")
    void TesteGetIdExistente() throws Exception {
        setupFormaPagamento();
        mockMvc.perform(get("/forma-pagamento/1")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Forma de Pagamento teste")));
    }

    @Test
    @DisplayName("Teste de id inexitente")
    void TesteGetIdInexistente() throws Exception {
        mockMvc.perform(get("/forma-pagamento/9999999")).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IdInexistenteException));
    }

    @Test
    @DisplayName("Teste de path errado")
    void TesteGetPathErrado() throws Exception {
        mockMvc.perform(get("/forma-pagamentoxptoyzujmkpto")).andExpect(status().isNotFound());
    }

    void setupFormaPagamento() {
        FormaPagamento formaPagamento = new FormaPagamento(
                BigInteger.ONE,
                "Forma de Pagamento teste",
                true);
        formaPagamentoRepository.save(formaPagamento);
    }

    @Test
    @DisplayName("Teste de post de nova Forma de Pagamento")
    void TestePostProduto() throws Exception {
        mockMvc.perform(
                post("/forma-pagamento")
                        .contentType("application/json")
                        .content("{\r\n" +
                                "  \"id\": 0,\r\n" +
                                "  \"descricao\": \"Forma de Pagamento teste\",\r\n" +
                                "  \"ativo\": true\r\n" +
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Teste de put de nova Forma de Pagamento")
    void TestePutFormaPagamento() throws Exception {
        setupFormaPagamento();
        mockMvc.perform(
                put("/forma-pagamento/1")
                        .contentType("application/json")
                        .content("{\r\n" +
                                "  \"id\": 1,\r\n" +
                                "  \"descricao\": \"Forma de Pagamento teste alterado\",\r\n" +
                                "  \"ativo\": true\r\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Forma de Pagamento teste alterado")));
    }

    @Test
    @DisplayName("Teste do delete")
    void testeDeleteFormaPagamento() throws Exception {
        setupFormaPagamento();
        mockMvc.perform(
                delete("/forma-pagamento/1")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Excluído")));
    }

}
