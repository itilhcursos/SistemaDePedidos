package br.com.itilh.bdpedidos.sistemapedidos.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import br.com.itilh.bdpedidos.sistemapedidos.repository.EstadoRepository;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EstadoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EstadoRepository estadoRepository;

    @Test
    @DisplayName("testando o retorno da busca da lista de estados pelo endPoint /estados")
    void testGetEstados() throws Exception{

        mockMvc.perform(get("/estados")).andExpect(status().isOk())
        .andExpect(content().string(containsString("totalElements")));

    }

    @Test
    @DisplayName("testando path inexistente")
    void testPathInexistente() throws Exception{
        mockMvc.perform(get("/estado")).andExpect(status().isMethodNotAllowed());
    }

    @Test
    @WithMockUser(username="admin", roles={"USER", "ADMIN"})
    @DisplayName("teste do endPoint para criar estado")
    void testCriarEstado() throws Exception{
       
        mockMvc.perform(
                post("/estado")
                .contentType("application/json")
                .content("{" + //
                            "  \"id\": null," + 
                            "  \"nome\": \"Estado de teste\"" + 
                        "}")
                        ).andExpect(status().isOk());

    }


}
