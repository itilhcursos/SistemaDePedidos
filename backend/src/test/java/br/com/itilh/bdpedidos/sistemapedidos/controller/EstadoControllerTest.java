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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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


    //o MockMvc é o que me permite simular a interação com o controlador - no caso ele permiti fazer os testes 
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
    //perform -> o que ele deve fazer(get, post, put, delete)

    @Test
    @DisplayName("Teste de retorno do Estado por id ")
    void testgetEstadoPorId() throws Exception{
        setUpEstado();
        mockMvc.perform(get("/estado/1")
        ).andExpect(status().isOk()).andExpect(content().string(containsString("Rio de Janeiro")));
    }

    @Test
    @DisplayName("teste de path inexistente")
    void TesteGetPathInexistente() throws Exception{
        setUpEstado();
        mockMvc.perform(get("/estado")).andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("teste de retorno de Estado por id inexistente")
    void testGetPorIdInexistente() throws Exception{
        setUpEstado();
        mockMvc.perform(get("/estado/5")).andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof IdInexistenteException));
    }

    @Test
    @DisplayName("Teste de Criar Estado ")
    void testPostEstado() throws Exception{
        mockMvc.perform(
        post("/estado")
        .contentType("application/json")
            .content("{\r\n" + //
                                "  \"id\": 0,\r\n" + //
                                "  \"nome\": \"Estado teste\"\r\n" + //
                                "}" )
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Teste de Alterar Estado ")
    void testPutEstado() throws Exception{
        setUpEstado();
        mockMvc.perform(
        put("/estado/1")
        .contentType("application/json")
            .content("{\r\n" + //
                                "  \"id\": 1,\r\n" + //
                                "  \"nome\": \"Estado teste\"\r\n" + //
                                "}" )
        ).andExpect(status().isOk()
        ).andExpect(content().string(containsString("Estado teste")));
    }

    @Test
    @DisplayName("teste de Exclusão do Estado")
    void TesteDeleteEstado() throws Exception{
        setUpEstado();
        mockMvc.perform(delete("/estado/1")).andExpect(status().isOk()
        ).andExpect(content().string(containsString("Excluído")));
    }



}

//isBadRequest -> requisição errada ou algum erro por parte do usuario(cliente)
//MethodNotAllowed -> metodo não encontrado, não existente