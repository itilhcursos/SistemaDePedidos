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



    @Test
    @DisplayName("teste path /formapagamento")
    void testGetTodosfomapagamento() throws Exception {
        //mapeamento errado @GetMapping("/formas-pagamento")
        
       mockMvc.perform(get("/formapagamento")).andExpect(status().isOk())
       .andExpect(content().string(containsString("totalElements")));

    }

    @Test 
    @DisplayName("teste de path inexistente")
    void TesteGetPathInexistente() throws Exception{
 //mapeamento errado @GetMapping("/formas-pagamento")
        mockMvc.perform(get("/formapagamento")).andExpect(status().isMethodNotAllowed());

    }

    @Test
    @DisplayName("teste de id existente ")
    void testGetIdExistente() throws Exception {
        testAlterarFormaPagamento();
         //mapeamento errado @GetMapping("/forma-pagamento/{id}")
        mockMvc.perform(get("/formapagamento/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("Formapagamento teste")));
    }

    @Test
    @DisplayName("Teste de id inexistente")
    void testGetIdInexistente() throws Exception {

    mockMvc.perform(get("/forma-pagamento/9999999")).andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof IdInexistenteException));
    }

    @Test
    @DisplayName("Teste do path errado")
    void testGetPathErrado() throws Exception {

        mockMvc.perform(get("/formapagamentoxpto")).andExpect(status().isNotFound());

    }

    //código perdido no meio da classe
    FormaPagamento formaPagamento = new FormaPagamento(
    BigInteger.ONE, "Forma de pagamento teste", true);
    formaPagamentoRepository.save(formaPagamento);
    //

@Test
@DisplayName("teste de post de novo FormaPagamento")
    void testCriarFormaPagamento() throws Exception {
        mockMvc.perform(post("/forma-pagamento").contentType("application/json").content("{\r\n" + //
                        "  \"id\": null,\r\n" + //
                        "  \"descricao\": \"Forma de pagamento teste\",\r\n" + //
                        "  \"ativo\": true\r\n" + //
                        "}"))
        .andExpect(status().isOk());
    }

    @Test
    @DisplayName("teste de put de novo formapagamento")
    void testAlterarFormaPagamento() throws Exception{
        // onde esta esse método?
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
    @DisplayName("Teste delete de Forma de Pagamento")
    void testDeletePorId() throws Exception {
          // onde esta esse método?
        setUpFormaPagamento();
        mockMvc.perform(delete("/forma-pagamento/1"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Excluído")));
    }


}