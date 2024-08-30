package br.com.itilh.bdpedidos.sistemapedidos.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Estado;
import br.com.itilh.bdpedidos.sistemapedidos.model.Municipio;
import br.com.itilh.bdpedidos.sistemapedidos.repository.EstadoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.MunicipioRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc // modelo, visão e controle
@ActiveProfiles("test") // para pegar as properties de teste
public class MunicipioControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EstadoRepository estadoRepository;

    @Autowired
    MunicipioRepository municipioRepository;

    @Test
    @DisplayName("Teste do path /municipios")
    void testGetMunicipios() throws Exception {
        mockMvc.perform(get("/municipios")).andExpect(status().isOk())
                .andExpect(content().string(containsString("totalElements")));

    }

    @Test
    @DisplayName("Teste de path inexistente")
    void testGetPathInexistente() throws Exception {
        mockMvc.perform(get("/municipio")).andExpect(status().isMethodNotAllowed());

    }

    @Test
    @DisplayName("Teste de id inexistente")
    void testGetIdInexistente() throws Exception {
        mockMvc.perform(get("/municipio/999999")).andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof IdInexistenteException));

    }

    // criação das entidades necessárias para os testes de existência de municipio
    void setupMunicipio() {
        setupEstado();
        Municipio municipio = new Municipio(
                BigInteger.ONE,
                "Municipio teste",
                true,
                estadoRepository.getReferenceById(BigInteger.ONE));
        municipioRepository.save(municipio);
    }

    @Test
    @DisplayName("Teste de id existente")
    void testGetIdExistente() throws Exception {
        setupMunicipio();
        mockMvc.perform(get("/municipio/1")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Municipio teste")));

    }

    // funçao para criar um estado n base h2 teste
    void setupEstado() {
        Estado estado = new Estado(BigInteger.ONE, "Estado teste");
        estadoRepository.save(estado);
    }

    @Test
    @DisplayName("Teste de post de novo Municipio")
    void TesteNovoMunicipio() throws Exception {
        setupEstado();
        mockMvc.perform(post("/municipio")
                .contentType("application/json")
                .content("{\r\n" + //
                        "  \"id\": 0,\r\n" + //
                        "  \"nome\": \"teste10\",\r\n" + //
                        "  \"entrega\": true,\r\n" + //
                        "  \"estadoId\": 1,\r\n" + //
                        "  \"estadoNome\": \"acre\"\r\n" + //
                        "}"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Teste de put de novo Municipio")
    void TestePutMunicipio() throws Exception {
        setupEstado();
        mockMvc.perform(
                put("/municipio/1")
                        .contentType("application/json")
                        .content("{\r\n" + //
                                "  \"id\": 1,\r\n" + //
                                "  \"nome\": \"Municipio teste Alterado\",\r\n" + //
                                "  \"entrega\": true,\r\n" + //
                                "  \"estadoId\": 1,\r\n" + //
                                "  \"estadoNome\": \"estado teste\"\r\n" + //
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Municipio teste Alterado")));

    }

    @Test
    @DisplayName("Teste do Delete")
    void testDeleteMunicipio() throws Exception {
        setupMunicipio();
        mockMvc.perform(delete("/municipio/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Excluído")));
    }
}
