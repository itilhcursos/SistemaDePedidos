package br.com.itilh.bdpedidos.sistemapedidos.controller;

import java.math.BigInteger;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")

public class FormaPagamentoControllerTest {

    @Autowired
    MockMvc mockMvc;
    
    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    void setUpFormaPagamento(){
        FormaPagamento formaPagamento = new FormaPagamento(
            BigInteger.ONE,
            "Forma de pagamento teste",
            true
        );
        formaPagamentoRepository.save(formaPagamento);
    }

    @Test
    void testAlterarFormaPagamento() throws Exception{
        setUpFormaPagamento();
        mockMvc.perform(put("/forma-pagamento/1").contentType("application/json").content("{\r\n" + //
                        "  \"id\": 1,\r\n" + //
                        "  \"descricao\": \"Forma de pagamento Alterado\",\r\n" + //
                        "  \"ativo\": true\r\n" + //
                        "}"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Forma de pagamento Alterado")));
    }

    @Test
    void testCriarFormaPagamento() throws Exception {
        mockMvc.perform(post("/forma-pagamento").contentType("application/json").content("{\r\n" + //
                        "  \"id\": 0,\r\n" + //
                        "  \"descricao\": \"Forma de pagamento teste\",\r\n" + //
                        "  \"ativo\": true\r\n" + //
                        "}"))
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Teste deletar Forma de Pagamento")
    void testDeletePorId() throws Exception {
        setUpFormaPagamento();
        mockMvc.perform(delete("/forma-pagamento/1"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Excluído")));
    }

    @Test
    @DisplayName("Teste do path /forma-pagamento/{id} INEXISTENTE")
    void testGetIdInexistente() throws Exception {
        mockMvc.perform(get("/forma-pagamento/9999999"))
        .andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof IdInexistenteException));
    }

    @Test
    @DisplayName("Teste do path /forma-pagamento/{id} EXISTENTE")
    void testGetPorIdExistente() throws Exception {
        setUpFormaPagamento();
        mockMvc.perform(get("/forma-pagamento/2")) // procuro por id 2 pois o Delete apaga o id1, e o setUp acima cria com id 2. //
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Forma de pagamento teste")));
    }

    @Test
    @DisplayName("Teste do path errado")
    void testGetPathErrado() throws Exception {
        mockMvc.perform(get("/formasx-pagamentozxsa"))
        .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Teste de path inexistente")
    void TesteGetPathInexistente() throws Exception{
        mockMvc.perform(get("/forma-pagamento"))
        .andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("Teste do path /formas-pagamento")
    void testGetTodos() throws Exception {
        mockMvc.perform(get("/formas-pagamento"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("totalElements")));
    }
}
