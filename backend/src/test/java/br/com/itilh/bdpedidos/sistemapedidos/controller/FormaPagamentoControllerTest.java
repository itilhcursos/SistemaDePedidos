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
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;


@SpringBootTest
@AutoConfigureMockMvc // só para controller
@ActiveProfiles("test") // para pegar as properties de teste
public class FormaPagamentoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    
    // criar banco de dados para teste do Controller
    @Test
    @DisplayName("testando o retorno da busca de forma de pagamentos")
    void testGetFormaPagamento() throws Exception {
        mockMvc.perform(get("/formas-pagamento")).andExpect(status().isOk())
                .andExpect(content().string(containsString("totalElements")));

    }

    @Test
    @DisplayName("testando path inexistente")
    void testPathInexistente() throws Exception {
        mockMvc.perform(get("/forma-pagamento")).andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("teste de path errado")
    void TesteGetPathErrado() throws Exception {
        mockMvc.perform(get("/forma-pagamento-xpto")).andExpect(status().isNotFound());
    }

    // criação das estidades necessárias parta os teste de existência de forma pagamento
    void setUpFormaPagamento() {
        setupFormaPagamento();
        FormaPagamento formaPagamento = new FormaPagamento(
                BigInteger.ONE,
                "FormaPagamento teste",
                true);

    }

    @DisplayName("Teste de ID existente")
    void TesteGetIdExistente() throws Exception {
        setupFormaPagamento(); // Certifique-se de que o dado foi salvo no repositório
        mockMvc.perform(get("/forma-pagamento/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("FormaPagamento teste")));
    }

    @Test
    @DisplayName("teste de id inexitente")
    void TesteGetIdInexistente() throws Exception {
        mockMvc.perform(get("/forma-pagamento/9999999")).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IdInexistenteException));
    }

    // função para criar uma Forma Pagamento
    void setupFormaPagamento() {
        FormaPagamento formaPagamento = new FormaPagamento(BigInteger.ONE, "FormaPagamento teste", true);
        formaPagamentoRepository.save(formaPagamento);
        
    }

    @Test
    @DisplayName("teste de post de nova Forma Pagamento")
    void TestPostFormaPagamento() throws Exception {
        setupFormaPagamento();
        mockMvc.perform(
                post("/forma-pagamento")
                        .contentType("application/json")
                        .content("{\r\n" + //
                                "  \"id\": 0,\r\n" + //
                                "  \"descricao\": \"string\",\r\n" + //
                                "  \"ativo\": true\r\n" + //
                                "}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Teste de put de Forma de Pagamento")
    void TestPutFormaPagamento() throws Exception {
        setupFormaPagamento();
        mockMvc.perform(
                put("/forma-pagamento/1")
                        .contentType("application/json")
                        .content("{\r\n" + //
                                "  \"id\": 1,\r\n" +
                                "  \"descricao\": \"Forma Pagamento teste Alterado\",\r\n" +
                                "  \"ativo\": true\r\n" + //
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Forma Pagamento teste Alterado")));
    }

    @Test
    @DisplayName("teste do endPoint para criar forma de pagamento")
    void testFormaPagamento() throws Exception {

        mockMvc.perform(
                post("/forma-pagamento")
                        .contentType("application/json")
                        .content("{\r\n" + //
                                "  \"id\": null,\r\n" + //
                                "  \"descricao\": \"Forma Pagamento teste\",\r\n" + //
                                "  \"ativo\": true\r\n" + //
                                "}"))
                .andExpect(status().isOk());

    }

    @Test
    @DisplayName(" Teste do delete")
    void testFormaPagamentoDelete() throws Exception {
        setUpFormaPagamento();
        mockMvc.perform(delete("/forma-pagamento/1")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Excluído")));
    }
   
}