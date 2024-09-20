package br.com.itilh.bdpedidos.sistemapedidos.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import br.com.itilh.bdpedidos.sistemapedidos.model.Estado;
import br.com.itilh.bdpedidos.sistemapedidos.repository.EstadoRepository;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EstadoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    EstadoRepository estadoRepository;



    void setupEstado(){
        Estado estado = new Estado(BigInteger.ONE, "Estado teste");
        estadoRepository.save(estado);
    }
    @Test
    @DisplayName("Teste do path errado")
    void TesteGetPathErrado() throws Exception{
        setupEstado();
        mockMvc.perform(get("/estaduus")).andExpect(status().isNotFound());
    }
    @Test
    @DisplayName("teste de buscar produto por id")
    void testGetEstadoPorId()throws Exception {
        setupEstado();
        mockMvc.perform(get("/estado/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("1")));
    }

    @Test
    @DisplayName("testando o retorno da busca da lista de estados pelo endPoint /estados")
    void testGetEstados() throws Exception{
setupEstado();
        mockMvc.perform(get("/estados")).andExpect(status().isOk())
        .andExpect(content().string(containsString("Estado teste")));

    }

    @Test
    @DisplayName("testando path inexistente")
    void testPathInexistente() throws Exception{
        mockMvc.perform(get("/estado")).andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("Teste do id existente")
    void testIdExistente() throws Exception {
        setupEstado();
        mockMvc.perform(get("/estado/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("1")));
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @DisplayName("teste do endPoint para criar estado")
    void testCriarEstado() throws Exception{
        setupEstado();
        mockMvc.perform(
                post("/estado")
                .contentType("application/json")
                .content("{" + //
                            "  \"id\": 1," + 
                            "  \"nome\": \"Estado de teste\"" + 
                        "}")
                        ).andExpect(status().isOk());

    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
@DisplayName("alterar estado")
    void testAlterarEstado() throws Exception{
        setupEstado();
         mockMvc.perform(put("/estado/1")
        .contentType("application/json")
        .content("{\r\n" + //
        "  \"id\": 1, \r\n" + //
        " \"nome\": \"estado teste alterado\",\r\n" + //
        "}")
        ).andExpect(status().isOk())
        .andExpect(content().string(containsString("estado teste alterado")));

    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @DisplayName("teste de delete do estado")
    void testDeleteEstado() throws Exception {
        setupEstado();
         mockMvc.perform(delete("/estado/1")
        ).andExpect(status().isOk())
        .andExpect(content().string(containsString("Excluído")));
    }



    @Test
    @DisplayName("Teste do id inexistente")
    void TesteGetIdInexistente() throws Exception{
        setupEstado();
        mockMvc.perform(get("/estado/1811818891")).andExpect(status().isBadRequest())
         .andExpect(result -> assertTrue(result.getResolvedException()instanceof IdInexistenteException));
    }


}
