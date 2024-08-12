package br.com.itilh.bdpedidos.sistemapedidos.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc // modelo, visão e controle
public class MunicipioControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Teste do path /municipios")
    void testGetMunicipios() throws Exception {
        mockMvc.perform(get("/municipios")).andExpect(status().isOk())
                .andExpect(content().string(containsString("totalElements")));

    }
}
