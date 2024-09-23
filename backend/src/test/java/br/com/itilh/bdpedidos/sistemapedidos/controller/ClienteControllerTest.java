package br.com.itilh.bdpedidos.sistemapedidos.controller;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.model.Municipio;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.MunicipioRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClienteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    MunicipioRepository municipioRepository;

    void setUpCliente() {
        setUpMunicipio();
        Cliente cliente = new Cliente(
            null,
            municipioRepository.getReferenceById(BigInteger.ONE),
            "Cliente Teste",
            "00.000.000/0001-00",
            "000.000.000-00",
            "99999-9999",
            "Endereço Teste",
            "Bairro Teste",
            "12345-678",
            "cliente@teste.com",
            true,
            "Informação adicional"
        );
        clienteRepository.save(cliente);
    }

    void setUpMunicipio() {
        Municipio municipio = new Municipio(
            BigInteger.ONE, 
            "Municipio Teste", 
            true, 
            null
        );
        municipioRepository.save(municipio);
    }

    @Test
    @DisplayName("Teste do path /clientes")
    void testGetClientes() throws Exception {
        mockMvc.perform(get("/clientes"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("totalElements")));
    }

    @Test
    @DisplayName("Teste de ID existente")
    void testGetIdExistente() throws Exception {
        setUpCliente();
        mockMvc.perform(get("/cliente/1"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Cliente Teste")));
    }

    @Test
    @WithMockUser(username="admin", roles={"USER","ADMIN"})
    @DisplayName("Teste de ID inexistente")
    void testGetIdInexistente() throws Exception {
        mockMvc.perform(get("/cliente/9999999"))
            .andExpect(status().isBadRequest())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof IdInexistenteException));
    }

    @Test
    @WithMockUser(username="admin", roles={"USER","ADMIN"})
    @DisplayName("Teste de criação de novo Cliente")
    void testPostCliente() throws Exception {
        setUpMunicipio();
        mockMvc.perform(
            post("/cliente")
            .contentType("application/json")
            .content("{\r\n" +
                    "  \"id\": null,\r\n" +
                    "  \"nomeRazaoSocial\": \"Cliente Teste\",\r\n" +
                    "  \"cnpj\": \"00.000.000/0001-00\",\r\n" +
                    "  \"cpf\": \"000.000.000-00\",\r\n" +
                    "  \"telefone\": \"99999-9999\",\r\n" +
                    "  \"endereco\": \"Endereço Teste\",\r\n" +
                    "  \"bairro\": \"Bairro Teste\",\r\n" +
                    "  \"cep\": \"12345-678\",\r\n" +
                    "  \"email\": \"cliente@teste.com\",\r\n" +
                    "  \"ativo\": true,\r\n" +
                    "  \"informacao\": \"Informação adicional\",\r\n" +
                    "  \"municipioId\": 1\r\n" +
                    "}")
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin", roles={"USER","ADMIN"})
    @DisplayName("Teste de alteração de Cliente")
    void testPutCliente() throws Exception {
        setUpCliente();
        mockMvc.perform(
            put("/cliente/1")
            .contentType("application/json")
            .content("{\r\n" +
                    "  \"id\": 1,\r\n" +
                    "  \"nomeRazaoSocial\": \"Cliente Teste Alterado\",\r\n" +
                    "  \"cnpj\": \"00.000.000/0001-00\",\r\n" +
                    "  \"cpf\": \"000.000.000-00\",\r\n" +
                    "  \"telefone\": \"99999-9999\",\r\n" +
                    "  \"endereco\": \"Endereço Teste\",\r\n" +
                    "  \"bairro\": \"Bairro Teste\",\r\n" +
                    "  \"cep\": \"12345-678\",\r\n" +
                    "  \"email\": \"cliente@teste.com\",\r\n" +
                    "  \"ativo\": true,\r\n" +
                    "  \"informacao\": \"Informação adicional\",\r\n" +
                    "  \"municipioId\": 1\r\n" +
                    "}")
        ).andExpect(status().isOk())
        .andExpect(content().string(containsString("Cliente Teste Alterado")));
    }

    @Test
    @WithMockUser(username="admin", roles={"USER","ADMIN"})
    @DisplayName("Teste de exclusão de Cliente")
    void testDeleteCliente() throws Exception {
        setUpCliente();
        mockMvc.perform(delete("/cliente/1"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Excluído")));
    }

    @Test
    @DisplayName("Teste de path inexistente")
    void testGetPathInexistente() throws Exception {
        mockMvc.perform(get("/clientexpto")).andExpect(status().isNotFound());
    }
}
