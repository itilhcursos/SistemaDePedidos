package br.com.itilh.bdpedidos.sistemapedidos.controller;

import java.math.BigInteger;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Estado;
import br.com.itilh.bdpedidos.sistemapedidos.repository.EstadoRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EstadoControllerTest {

    @Autowired
    
    EstadoRepository estadoRepository;

    @Autowired
    MockMvc mockMvc;

    void setUpEstado(){
        Estado estado = new Estado(
            BigInteger.ONE,
            "Rio de Janeiro"
        );
        estadoRepository.save(estado);
    }

    @Test
    @DisplayName("Teste de retorno dos Estados ")
    void testgetTodos() throws Exception{
        setUpEstado();
        mockMvc.perform(get("/estados")
        ).andExpect(status().isOk()).andExpect(content().string(containsString("Rio de Janeiro")));
    }

    @Test
    @DisplayName("Teste de retorno do Estado pro id ")
    void testgetEstadoPorId() throws Exception{
        setUpEstado();
        mockMvc.perform(get("/estado/1")
        ).andExpect(status().isOk()).andExpect(content().string(containsString("Rio de Janeiro")));
    }


    @Test
    @DisplayName("teste de retorno de Estado por id inexistente")
    void testGetPorIdInexistente() throws Exception{
        setUpEstado();
        mockMvc.perform(get("/estado/5")).andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof IdInexistenteException));
    }

}
