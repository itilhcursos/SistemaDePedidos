package br.com.itilh.bdpedidos.sistemapedidos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.math.BigInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.dto.EstadoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.EstadoDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Estado;
import br.com.itilh.bdpedidos.sistemapedidos.repository.EstadoRepository;

@SpringBootTest
@ActiveProfiles("test")
public class EstadoServiceTest {

    @Autowired
    EstadoService estadoService;

    @Autowired
    EstadoRepository estadoRepository;

void setupEstado(){
    Estado estado = new Estado(BigInteger.ONE,"Estado Teste");
    estadoRepository.save(estado);
}



    @Test
    void testAlterarEstado() {

    }

    @Test
    @DisplayName("Test de criação de um estado")
    void testCriarEstado() throws Exception {
        setupEstado();
        EstadoDTO testeDTO = new EstadoDTO(BigInteger.ONE, "Estado Teste");
        EstadoDTO retono = estadoService.criarEstado(testeDTO);
        assertEquals(true, retono.getId()!=null);
    }

    @Test
    @DisplayName("Test de criação de um estado duplicado")
    void testEstadoDuplicado() throws Exception {
        setupEstado();
        EstadoDTO testeDTO = new EstadoDTO(BigInteger.ONE, "estado de teste duplicado");

        EstadoDTO retono = estadoService.criarEstado(testeDTO);
        assertThrows(EstadoDuplicadoException.class, ()-> estadoService.criarEstado(testeDTO));
    }

    @Test
    @DisplayName("teste de excluir estado")
    void testeExcluirEstadoPorId()throws Exception {
        setupEstado();
        estadoService.excluirEstadoPorId(BigInteger.ONE);

    EstadoDTO estadoDTO = estadoService.buscarEstadoPorId(BigInteger.ONE);
    assertNull(estadoDTO);
    }

    @Test
    void testBuscarEstadoPorId() {

    }

    @Test
    void testListarEstados() {

    }
}
