package br.com.itilh.bdpedidos.sistemapedidos.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import br.com.itilh.bdpedidos.sistemapedidos.exception.BadArgumentsException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MunicipioControllerTest {
   
    @Autowired
    MockMvc mockMvc;

    @Test
    void testGetMunicipios() throws Exception {
        mockMvc.perform(get("/municipios")).andExpect(status().isOk());
    }

    @Test
    void testGetMunicipio() throws Exception {
        mockMvc.perform(get("/municipio")).andExpect(status().isMethodNotAllowed());
    }

    @Test
    void testGetMunicipioid() throws Exception {
        mockMvc.perform(get("/municipio/1")).andExpect(status().isOk());
    }

    @Test
    void testGetMunicipioidInexistente() throws Exception {
        try {
            mockMvc.perform(get("/municipio/10000"))
            .andExpect(status().isInternalServerError());
        } catch (Exception e) {
        }
    }


    @Test
    void testPostMunicipio() throws Exception {
        mockMvc.perform(
            post("/municipio")
            .contentType("application/json")
            .content("{  \"id\": 0,\r\n" + 
                    "  \"nome\": \"teste\",\r\n" + 
                    "  \"entrega\": true,\r\n" + 
                    "  \"estadoId\": 1,\r\n" + 
                    "   \"estadoNome\": \"acre\"\r\n" + "}")
        ).andExpect(status().isOk());
    }

    @Test
    void testPostMunicipioNomeinvalido() throws Exception {
        mockMvc.perform(
            post("/municipio")
            .contentType("application/json")
            .content("{  \"id\": 0,\r\n" + 
                    "  \"nomess\": \"teste\",\r\n" + 
                    "  \"entrega\": true,\r\n" + 
                    "  \"estadoId\": 1,\r\n" + 
                    "   \"estadoNome\": \"acre\"\r\n" + "}")
        )
        .andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof BadArgumentsException))
      .andExpect(result -> assertEquals("Dados inválidos", result.getResolvedException().getMessage()));
    }
}
