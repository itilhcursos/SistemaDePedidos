package br.com.itilh.bdpedidos.sistemapedidos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.dto.EstadoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.EstadoDuplicadoException;

@SpringBootTest
@ActiveProfiles("test")
public class EstadoServiceTest {

    @Autowired
    EstadoService service;

    @Test
    void testAlterarEstado() {

    }

    @Test
    @DisplayName("Test de criação de um estado")
    void testCriarEstado() throws Exception {

        EstadoDTO testeDTO = new EstadoDTO(null, "estado de teste");
        EstadoDTO retorno = service.criarEstado(testeDTO);
        assertEquals(true, retorno.getId()!=null);
    }

    @Test
    @DisplayName("Test de criação de um estado duplicado")
    void testEstadoDuplicado() throws Exception {

        EstadoDTO testeDTO = new EstadoDTO(null, "estado de teste duplicado");

        EstadoDTO retono = service.criarEstado(testeDTO);
        assertThrows(EstadoDuplicadoException.class, ()-> service.criarEstado(testeDTO));
    }

    @Test
    void testDeletePorId() {

    }

    @Test
    void testGetPorId() {

    }

    @Test
    void testGetTodos() {

    }
}
