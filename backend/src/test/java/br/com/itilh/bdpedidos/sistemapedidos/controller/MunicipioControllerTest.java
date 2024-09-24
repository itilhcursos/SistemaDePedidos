package br.com.itilh.bdpedidos.sistemapedidos.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;;

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

    // criar banco de dados para teste do Controller

    @Test
    @DisplayName("teste do path /municipios")
    void testGetMunicipios() throws Exception {
        mockMvc.perform(get("/municipios")).andExpect(status().isOk())
        .andExpect(content().string(containsString("totalElements")));

    }

    @Test
    @DisplayName("teste de path inexistente")
    void TesteGetPathInexistente() throws Exception{
        mockMvc.perform(get("/municipio")).andExpect(status().isMethodNotAllowed());
    }

    // criação das estidades necessárias parta os teste de existência de municipio
    void setUpMunicipio(){
        setupEstado();
        Municipio municipio = new Municipio(
            BigInteger.ONE,
            "Municipio teste",
            true,
            estadoRepository.getReferenceById(BigInteger.ONE));
        municipioRepository.save(municipio);
    }

    @Test
    @DisplayName("teste de id existente ")
    void TesteGetIdExistente() throws Exception{
        setUpMunicipio();
        mockMvc.perform(get("/municipio/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("Municipio teste")));
    }


    @Test
    @DisplayName("teste de id inexitente")
    void TesteGetIdInexistente() throws Exception{
        mockMvc.perform(get("/municipio/9999999")).andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof IdInexistenteException));
    }

    @Test
    @DisplayName("teste de path errado")
    void TesteGetPathErrado() throws Exception{
        mockMvc.perform(get("/municipioxpto")).andExpect(status().isNotFound());
    }

    // função para criar um Estado na base H2 teste
    void setupEstado(){
        Estado estado = new Estado(BigInteger.ONE, "Estado teste");
        estadoRepository.save(estado);
    }

    @Test
    @WithMockUser(username="admin", roles={"USER", "ADMIN"})
    @DisplayName("teste de post de novo Municipio")
    void TestePostMunicipio() throws Exception{
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
                                "}")        
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin", roles={"USER", "ADMIN"})
    @DisplayName("teste de put de novo Municipio")
    void TestePutMunicipio() throws Exception{
        setUpMunicipio();
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
        ).andExpect(status().isOk()
        ).andExpect(content().string(containsString("Municipio teste Alterado")));
    }

    @Test
    @WithMockUser(username="admin", roles={"USER", "ADMIN"})
    @DisplayName(" Teste do delete")
    void testeDeleteMunicipio() throws Exception{
        setUpMunicipio();
        mockMvc.perform( delete("/municipio/1")
        ).andExpect(status().isOk()
        ).andExpect(content().string(containsString("Excluído")));
    }






}
