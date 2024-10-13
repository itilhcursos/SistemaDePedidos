package br.com.itilh.bdpedidos.sistemapedidos.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;

import static org.hamcrest.Matchers.containsString;
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
        @DisplayName("Teste Alterar Forma Pagamento")
        void testAlterarFormaPagamento() throws Exception {
                setupFormaPagamento();
                mockMvc.perform(put("/forma-pagamento/1")
                                .contentType("application/json")
                                .content("{\r\n" + //
                                                "  \"id\": 1," +
                                                "  \"descricao\": \"Forma pagamento Alterado\",\r\n" + //
                                                "  \"ativo\": true\r\n" + //
                                                "}"))
                                .andExpect(status().isOk())
                                .andExpect(content().string(containsString("Forma pagamento Alterado")));

        }

        @Test
        @DisplayName("Teste de post de nova forma de pagamento")
        void testCriarFormaPagamento() throws Exception {
                mockMvc.perform(
                                post("/forma-pagamento")
                                                .contentType("application/json")
                                                .content("{\r\n" + //
                                                                "  \"id\": null," +
                                                                "  \"descricao\": \"Forma Pagamento teste\",\r\n" + //
                                                                "  \"ativo\": true\r\n" + //
                                                                "}"))
                                .andExpect(status().isOk());

        }

        @Test
        @DisplayName("Teste do Delete")
        void testDeletePorId() throws Exception {
                setupFormaPagamento();
                mockMvc.perform(delete("/forma-pagamento/1"))
                                .andExpect(status().isOk())
                                .andExpect(content().string(containsString("Excluído")));

        }

        @Test
        @DisplayName("Teste Get por ID")
        void testGetPorId() throws Exception {
                setupFormaPagamento();
                mockMvc.perform(get("/forma-pagamento/1"))
                                .andExpect(status().isOk())
                                .andExpect(content().string(containsString("Forma Pagamento teste")));

        }

        @Test
        @DisplayName("teste de busca Forma de Pagamento")
        void testGetFormaPagamento() throws Exception {
                mockMvc.perform(get("/formas-pagamento")).andExpect(status().isOk())
                                .andExpect(content().string(containsString("totalElements")));

        }

        void setupFormaPagamento() {
                FormaPagamento formaPagamento = new FormaPagamento(BigInteger.ONE,
                                "Forma Pagamento teste", true);
                formaPagamentoRepository.save(formaPagamento);
        }

        @Test
        @DisplayName("Teste de id existente")
        void testGetIdExistente() throws Exception {
                setupFormaPagamento();
                mockMvc.perform(get("/forma-pagamento/1")).andExpect(status().isOk())
                                .andExpect(content().string(containsString("Forma Pagamento teste")));
        }
}
