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

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;

@SpringBootTest
@AutoConfigureMockMvc // só para controller
@ActiveProfiles("test") // para pegar as properties de teste
public class MunicipioControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EstadoRepository estadoRepository;

    @Autowired
    MunicipioRepository municipioRepository;

    @Test
    @DisplayName("Teste do Path / municípios")
    void testGetMunicipios() throws Exception {
        mockMvc.perform(get("/municipios")).andExpect(status().isOk())
                .andExpect(content().string(containsString("totalElements")));
    }

    //Função para criar um Municipio na base H2 teste
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
    @DisplayName("Teste de Id Existente")
    void testGetPathExistente() throws Exception {
        setupMunicipio();
        mockMvc.perform(get("/municipio/1")).andExpect(status().isOk())
                .andExpect(content().string(containsString("Municipio teste")));
    }

    @Test
    @DisplayName("Teste de Id Inexistente")
    void testGetPathInexistente() throws Exception {
        mockMvc.perform(get("/municipio/99999")).andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof IdInexistenteException));
    }

    @Test
    @DisplayName("Teste do Path Errado")
    void testGetPathErrado() throws Exception {
        mockMvc.perform(get("/municipioxpto")).andExpect(status().isNotFound());
    }

    //Função para criar um Estado na base H2 teste
    void setupEstado() {
        Estado estado = new Estado(BigInteger.ONE, "Estado teste");
        estadoRepository.save(estado);
    }

    @Test
    @DisplayName("Teste de Post Município")
    void testPostMunicipio() throws Exception {
        setupEstado();
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

    @Test
    @DisplayName("Teste do Put Município")
    void testPutMunicipio() throws Exception {
        setupEstado();
        mockMvc.perform(
                put("/municipio/1")
                        .contentType("application/json")
                        .content("{\r\n" + //
                                "  \"id\": 1,\r\n" + //
                                "  \"nome\": \"Municipio teste Alterado\",\r\n" + //
                                "  \"entrega\": true,\r\n" + //
                                "  \"estadoId\": 1,\r\n" + //
                                "  \"estadoNome\": \"estado Teste\"\r\n" + //
                                "}")
                                ).andExpect(status().isOk())
                                .andExpect(content().string(containsString("Municipio teste Alterado")));
    }

    @Test
    @DisplayName("Teste do Delete Município")
    void testDeleteMunicipio() throws Exception {
        setupMunicipio();
        mockMvc.perform(
                delete("/municipio/1")
                ).andExpect(status().isOk()
                ).andExpect(content().string(containsString("Excluído")));
                             
    }
}


