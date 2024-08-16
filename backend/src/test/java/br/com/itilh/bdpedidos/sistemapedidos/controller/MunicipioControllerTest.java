package br.com.itilh.bdpedidos.sistemapedidos.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MunicipioControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Teste do path / municipios")
    void testGetMunicipios() throws Exception {
        mockMvc.perform(get("/municipios")).andExpect(status().isOk())
                .andExpect(content().string(containsString("totalElements")));
    }

    @Test
    @DisplayName("Teste de Id Existente")
    void testGetPathExistente() throws Exception {
        mockMvc.perform(get("/municipio/1")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Assis Brasil")));
    }

    @Test
    @DisplayName("Teste de Id Inexistente")
    void testGetPathInexistente() throws Exception {
        mockMvc.perform(get("/municipio/99999")).andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof IdInexistenteException));
    }

    @Test
    @DisplayName("Teste do Path errado")
    void testGetPathErrado() throws Exception {
        mockMvc.perform(get("/municipioxpto")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Teste do Path errado")
    void testPostMunicipio() throws Exception {
        mockMvc.perform(
                post("/municipio")
                        .contentType("application/json")
                        .content("{\r\n" + //
                                "  \"id\": 0,\r\n" + //
                                "  \"nome\": \"Municipio teste\",\r\n" + //
                                "  \"entrega\": true,\r\n" + //
                                "  \"estadoId\": 1,\r\n" + //
                                "  \"estadoNome\": \"acre\"\r\n" + //
                                "}"))
                .andExpect(status().isOk());
    }
}


