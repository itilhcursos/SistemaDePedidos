package br.com.itilh.bdpedidos.sistemapedidos.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

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

}