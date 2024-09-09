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
    EstadoService estadoService;


   

    @Test
    @DisplayName("teste de Regra de Negocio - Criar Estado")
    void testCriarEstado() throws Exception{
        EstadoDTO estado = new EstadoDTO(null, "Estado teste");

        estado = estadoService.criarEstado(estado);

        assertEquals(true, estado.getId() != null);

    }

    @Test
    @DisplayName("teste de Regra de Negocio - Criar Estado duplicado")
    void testCriarEstadoDuplicado() throws Exception{
        EstadoDTO estado = new EstadoDTO(null, "teste");
        EstadoDTO Return = estadoService.criarEstado(estado);

        assertThrows(EstadoDuplicadoException.class, ()-> estadoService.criarEstado(estado));

    }

    @Test
    @DisplayName("teste de Regra de Negocio - Alterar Estado ")
    void testAlterarEstado() throws Exception{
        
        EstadoDTO estado = new EstadoDTO(null, "Estado teste" );
        EstadoDTO estadoRetorno = estadoService.criarEstado(estado);

        EstadoDTO estadoAlterado = new EstadoDTO(estadoRetorno.getId(), "Estado teste alterado" );
        estadoAlterado = estadoService.alterarEstado(estadoAlterado.getId(), estadoAlterado);

        assertEquals(true, estadoAlterado.getId().equals(estadoRetorno.getId()));
    }


    //tirar duvidas com o professor 
    @Test
    @DisplayName("teste de Regra de Negocio - Alterar Estado Duplicado")
    void testCriarEstadoDuplicadoPorAlteracao() throws Exception {
        
        EstadoDTO  dtoNomeErrado = new EstadoDTO (null, "teste");
        EstadoDTO dtoReturn = estadoService.criarEstado(dtoNomeErrado);

        EstadoDTO  dtoCorrigido = new EstadoDTO (null, "Nome corrigido");
        EstadoDTO Return2 = estadoService.criarEstado(dtoCorrigido);
        
        EstadoDTO  dtoNomeCorrigido = new EstadoDTO (dtoReturn.getId(), "Nome corrigido ");
         
        assertThrows(EstadoDuplicadoException.class , () -> estadoService.alterarEstado(dtoNomeCorrigido.getId(), dtoNomeCorrigido));

       
    }


}
