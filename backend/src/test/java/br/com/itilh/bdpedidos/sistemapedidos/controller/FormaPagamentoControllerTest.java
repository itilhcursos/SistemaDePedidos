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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;;

@SpringBootTest
@AutoConfigureMockMvc // só para controller
@ActiveProfiles("test") // para pegar as properties de teste
public class FormaPagamentoControllerTest {

    @Autowired
    MockMvc mockMvc;
    

    @Autowired
    FormaPagamentoRepository FormaPagamentoRepository;

    // criar banco de dados para teste do Controller

    @Test
    @DisplayName("Teste do path /formas de pagamento")
    void testGetFormasPagamento() throws Exception {
        mockMvc.perform(get("/formas-Pagamento")).andExpect(status().isOk())
        .andExpect(content().string(containsString("totalElements")));

    }

    @Test
    @DisplayName("teste de path inexistente")
    void TesteGetPathInexistente() throws Exception{
        mockMvc.perform(get("/FormaPagamento")).andExpect(status().isMethodNotAllowed());
    }


    @Test
    @DisplayName("teste de id existente ")
    void TesteGetIdExistente() throws Exception{
        setupFormaPagamento();
        mockMvc.perform(get("/forma-pagamento/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("Forma de Pagamento")));
    }


    @Test
    @DisplayName("teste de id inexitente")
    void TesteGetIdInexistente() throws Exception{
        mockMvc.perform(get("/formapagamento/9999999")).andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof IdInexistenteException));
    }

    @Test
    @DisplayName("teste de path errado")
    void TesteGetPathErrado() throws Exception{
        mockMvc.perform(get("/forma-pagamentoxpto")).andExpect(status().isNotFound());
    }

    void setupFormaPagamento(){
        FormaPagamento formaPagamento = new FormaPagamento(
            BigInteger.ONE,
            "Forma de Pagamento",
            true);
            FormaPagamentoRepository.save(formaPagamento);
    }


    @Test
    @DisplayName("teste de post de nova FormaPagamento")
    void TestePostFormaPagamento() throws Exception{
        setupFormaPagamento();
        mockMvc.perform( 
            post("/formapagamento")
            .contentType("application/json")
            .content("{\r\n" + //
                    "  \"id\": 0,\r\n" + //
                    "  \"descricao\": \"FormaPagamento teste\",\r\n" + //
                    "  \"ativo\": true,\r\n" + //
                                "}")        
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("teste de put de nova FormaPagamento")
    void TestePutFormaPagamento() throws Exception{
        setupFormaPagamento();
        mockMvc.perform( 
            put("/formapagamento/1")
            .contentType("application/json")
            .content("{\r\n" + //
                    "  \"id\": 1,\r\n" + 
                    "  \"descricao\": \"FormaPagamento teste Alterado\",\r\n" + 
                    "  \"ativo\": true,\r\n" + //
                                "}")        
        ).andExpect(status().isOk()
        ).andExpect(content().string(containsString("FormaPagamento teste Alterado")));
    }

    @Test
    @DisplayName(" Teste do delete")
    void testeDeleteFormaPagamento() throws Exception{
        setupFormaPagamento();
        mockMvc.perform( delete("/formapagamento/1")
        ).andExpect(status().isOk()
        ).andExpect(content().string(containsString("Excluído")));
    }






}
