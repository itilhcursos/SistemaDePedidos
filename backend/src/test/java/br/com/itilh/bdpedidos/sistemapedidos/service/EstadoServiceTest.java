package br.com.itilh.bdpedidos.sistemapedidos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.itilh.bdpedidos.sistemapedidos.dto.EstadoDTO;

@SpringBootTest
public class EstadoServiceTest {


    @Autowired
    EstadoService estadoService;


   
   

    @Test
    @DisplayName("teste de Regra de Negocio - Criar Estado")
    void testCriarEstado() throws Exception{
        EstadoDTO estado = new EstadoDTO(null, "Estado teste");

        estado = estadoService.criarEstado(estado);

        assertEquals(true, estado.getId() != null);

    }

    @Test
    @DisplayName("teste de Regra de Negocio - Alterar Estado ")
    void testAlterarEstado() throws Exception{
        
        EstadoDTO estado = new EstadoDTO(null, "Estado teste" );
        EstadoDTO estadoRetorno = estadoService.criarEstado(estado);

        EstadoDTO estadoAlterado = new EstadoDTO(estadoRetorno.getId(), "Estado teste alterado" );
        estadoAlterado = estadoService.alterarEstado(estadoAlterado.getId(), estadoAlterado);
    }
}
