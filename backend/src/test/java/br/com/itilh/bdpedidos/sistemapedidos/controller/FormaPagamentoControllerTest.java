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
@ActiveProfiles("test")
public class FormaPagamentoControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    @Test
    @DisplayName("teste do path /formas-pagamento")
    void testGetFormasPagamento() throws Exception {
        mockMvc.perform(get("/formas-pagamento")).andExpect(status().isOk())
        .andExpect(content().string(containsString("totalElements")));
    }

    @Test
    @DisplayName("teste de path inexistente")
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
    @DisplayName("teste de id existente ")
    void TesteGetIdExistente() throws Exception{
        setUpFormaPagamento();
        mockMvc.perform(get("/forma-pagamento/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("PIX")));
    }

    @Test
    @DisplayName("teste de id inexistente")
    void TesteGetIdInexistente() throws Exception{
        mockMvc.perform(get("/forma-pagamento/9999999")).andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof IdInexistenteException));
    }

    @Test
    @DisplayName("teste de path errado")
    void TesteGetPathErrado() throws Exception{
        mockMvc.perform(get("/forma-pagamentoerrado")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("teste post de nova forma-pagamento")
    void TestePostFormaPagamento() throws Exception{
        mockMvc.perform( 
            post("/forma-pagamento")
            .contentType("application/json")
            .content("{\n" +
                    "  \"id\": 1,\n" +
                    "  \"descricao\": \"Cheque\",\n" +
                    "  \"ativo\": true\n" +
                    "}")        
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("teste put de nova forma pagamento")
    void TestePutFormaPagamento() throws Exception {
        setUpFormaPagamento();
        mockMvc.perform(
                put("/forma-pagamento/1")
                        .contentType("application/json")
                        .content("{\r\n" + //
                                "  \"id\": 1,\r\n" + //
                                "  \"descricao\": \"forma de pagamento teste alterado\",\r\n" + //
                                "  \"ativo\": true\r\n" + //
                                "}"))
                .andExpect(status().isOk()).andExpect(content().string(containsString("forma de pagamento teste alterado")));
    }

    @Test
    @DisplayName("Teste do delete")
    void testeDeleteFormaPagamento() throws Exception {
        setUpFormaPagamento();
        mockMvc.perform(delete("/forma-pagamento/1")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Excluído")));
 
            }
}
