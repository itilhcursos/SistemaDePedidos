package br.com.itilh.bdpedidos.sistemapedidos.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Estado;
import br.com.itilh.bdpedidos.sistemapedidos.model.Municipio;
import br.com.itilh.bdpedidos.sistemapedidos.repository.EstadoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.MunicipioRepository;
import jakarta.persistence.EntityManager;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigInteger;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class MunicipioControllerTest {

    @Autowired
    EstadoRepository estadoRepository;

    @Autowired
    MunicipioRepository municipioRepository;

    @Autowired
    MockMvc mockMvc;


   private void setupEstado(){
        Estado estado = new Estado();
        estado.setId(BigInteger.ONE);
        estado.setNome("estado Teste");
        this.estadoRepository.save(estado);
   }

    private void setupMunicipio(){
        setupEstado();
        Municipio municipio = new Municipio();
        municipio.setId(BigInteger.ONE);
        municipio.setNome("municipio teste");
        municipio.setEntrega(true);
        municipio.setEstado(this.estadoRepository.getReferenceById(BigInteger.ONE));
        this.municipioRepository.save(municipio);
    }


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

    @Test
    @DisplayName("teste de id existente ")
    void TesteGetIdExistente() throws Exception{

        this.setupMunicipio();
        
        mockMvc.perform(get("/municipio/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("municipio teste")));
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

    @Test
    @DisplayName("teste de post de novo Municipio")
    void TestePostMunicio() throws Exception{

        this.setupEstado();

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






}
