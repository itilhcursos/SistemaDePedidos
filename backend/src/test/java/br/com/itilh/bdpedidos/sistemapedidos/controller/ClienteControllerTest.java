package br.com.itilh.bdpedidos.sistemapedidos.controller;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClienteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ClienteRepository clienteRepository;

    @Test
    @DisplayName("Testando o retorno da busca pelo endpoinst /clientes")
    void testGetClientes() throws Exception {
        mockMvc.perform(get("/clientes")).andExpect(status().isOk())
                .andExpect(content().string(containsString("totalElements")));
    }

    @Test
    @DisplayName("Testando path inexixtente")
    void testPathInexistente() throws Exception {
        mockMvc.perform(get("/cliente")).andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("Teste de id inexistente")
    void testGetIdInexistente() throws Exception {
        mockMvc.perform(get("/cliente/999999")).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IdInexistenteException));
    }

    @Test
    @DisplayName("Teste de id existente")
    void testGetIdExistente() throws Exception {
        setUpCliente();
        mockMvc.perform(get("/cliente/1")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Cliente Teste")));
    }

    void setUpCliente() {
        Cliente cliente = new Cliente();
        clienteRepository.save(cliente);
    }

    @Test
    @DisplayName("Teste de post de novo Cliente")
    void TesteNovoCliente() throws Exception {
        setUpCliente();
        mockMvc.perform(post("/cliente")
                .contentType("application/json")
                .content("{\r\n" + //
                        "  \"id\": 0,\r\n" + //
                        "  \"nome\": \"teste10\",\r\n" +
                        "}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Teste de put de novo cliente")
    void TestePutCliente() throws Exception {
        setUpCliente();
        mockMvc.perform(
                put("/cliente/1")
                        .contentType("application/json")
                        .content("{\r\n" +
                                "  \"id\": 1,\r\n" +
                                "  \"nome\": \"Municipio teste Alterado\",\r\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Cliente Alterado")));

    }

    private MockHttpServletRequestBuilder put(String string) {
        throw new UnsupportedOperationException("Unimplemented method 'put'");
    }

    @Test
    @DisplayName("Teste do delet")
    void testDeletCliente() throws Exception {
        setUpCliente();
        mockMvc.perform(delete("/cliente/1")).andExpect(status().isOk())
                .andExpect(content().string(containsString("excluido")));
    }
}
