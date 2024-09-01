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

//para poder usar o @AutoConfigureMockMvc tem que conter o @SpringBootTest
@SpringBootTest // Dizer que é essa classe é uma classe de teste
@AutoConfigureMockMvc // é o cara que ira dar algumas opções de modelo, visão e controle
// ele esta preparado para trabalhar com Mvc Web ou seja mandando requisição ou tratando requisição via http
// M-modelo , V- visão, C- controle; Mock -> Mockito quer dizer que vai criar um fake, um mockito do controller para que possa testalo
@ActiveProfiles("test")
public class FormaPagamentoControllerTest {

    @Autowired 
    MockMvc mockMvc; // toda vez que por o @AutoConfigureMockMvc tera que instanciar o MockMvc

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    

    @Test
    @DisplayName("Teste de retorno das formas de pagamento ")
    void testGetTodos() throws Exception {
        mockMvc.perform(get("/formas-pagamento")
        ).andExpect(status().isOk()).andExpect(content().string(containsString("totalElements")));
    }


    @Test
    @DisplayName("teste de Path Inexistente")
    void TesteGetPathInexistente() throws Exception {
        mockMvc.perform(get("/forma-pagamento")).andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("teste de retorno de Forma de Pagamento por id")
    void testGetPorId() throws Exception {
        setUpFormaPagamento();
        mockMvc.perform(get("/forma-pagamento/1")).andExpect(status().isOk())
        .andExpect(status().isOk()).andExpect(content().string(containsString("Dinheiro"))); //se colocar a Descrição que é Débito ou Crédito ira dar erro, creio que devido ao acento mesmo sendo a mesma palavra
    }

    @Test
    @DisplayName("teste de retorno de Forma de Pagamento por id inexistente")
    void testGetPorIdInexistente() throws Exception {
        mockMvc.perform(get("/forma-pagamento/500")).andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof IdInexistenteException));
    }


    void setUpFormaPagamento(){
        FormaPagamento formaPagamento = new FormaPagamento(
            BigInteger.ONE,
            "Dinheiro",
            true
        );
        formaPagamentoRepository.save(formaPagamento);
    }

    @Test
    @DisplayName("Teste de Criar forma de pagamento")
    void testCriarFormaPagamento() throws Exception {
        mockMvc.perform(
        post("/forma-pagamento")
        .contentType("application/json")
        .content("{\r\n" + //
                        "  \"id\": 25,\r\n" + //
                        "  \"descricao\": \"test\",\r\n" + //
                        "  \"ativo\": false\r\n" + //
                        "}")
        ).andExpect(status().isOk());
    }   

    @Test
    @DisplayName("Teste de alterar a forma de pagamento")
    void testAlterarFormaPagamento() throws Exception {
        setUpFormaPagamento();
        mockMvc.perform(
        put("/forma-pagamento/1")
        .contentType("application/json")
        .content("{\r\n" + //
                        "  \"id\": 1,\r\n" + //
                        "  \"descricao\": \"test\",\r\n" + //
                        "  \"ativo\": true\r\n" + //
                        "}")
        ).andExpect(status().isOk())
        .andExpect(content().string(containsString("test")));
    }


    @Test
    void testDeletePorId() throws Exception{
        setUpFormaPagamento();
        mockMvc.perform(
        delete("/forma-pagamento/1"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Excluido")));
    }

    

   // mockMvc.perform(get("/")) quer dizer qual é o comando que voçe quer dar
   //andExpect(status().isOk()) e qual é o resultado esperando de retorno
}
