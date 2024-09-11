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
import org.springframework.data.domain.Page;
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
    @DisplayName("teste de alterar estado")
    void testAlterarEstado()throws Exception {
    setupEstado();
    EstadoDTO dtoNomeErrado = new EstadoDTO(BigInteger.ONE,"estado errado");
    EstadoDTO dtoRetorno = estadoService.criarEstado(dtoNomeErrado);
    EstadoDTO dtoNomeCorrigido = new EstadoDTO(dtoRetorno.getId(),"estado corrigido");
    dtoNomeCorrigido = estadoService.alterarEstado(dtoRetorno.getId(), dtoNomeCorrigido);
    assertEquals(true, dtoNomeCorrigido.getId().equals(dtoRetorno.getId()));
}

@Test
@DisplayName("teste de estado duplicado por alteração")
void testAlterarEstadoDuplicado()throws Exception{
    setupEstado();
    EstadoDTO dtoNomeErrado = new EstadoDTO(BigInteger.ONE,"estado errado");
    dtoNomeErrado = estadoService.criarEstado(dtoNomeErrado);
    EstadoDTO dtoNomeCorrigido = new EstadoDTO(BigInteger.ONE,"estado corrigido");
    dtoNomeCorrigido = estadoService.criarEstado(dtoNomeCorrigido);
    
    EstadoDTO dtoCorrigido = new EstadoDTO(dtoNomeErrado.getId(),"corrigido");
assertThrows(EstadoDuplicadoException.class,()-> estadoService.alterarEstado(dtoCorrigido.getId(), dtoCorrigido));
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
   @DisplayName("teste de excluir estado nao existente")
void testExcluirEstadoNaoExistente()throws Exception {
setupEstado();
estadoService.excluirEstadoPorId(BigInteger.TEN);
EstadoDTO estadoDTO = estadoService.buscarEstadoPorId(BigInteger.ONE);
assertEquals(BigInteger.ONE, estadoDTO);
}


@Test
@DisplayName("teste de buscar estado por id")
    void testBuscarEstadoPorId()throws Exception  {
setupEstado();
EstadoDTO estadoDTO = estadoService.buscarEstadoPorId(BigInteger.ONE);
assertEquals(BigInteger.ONE, estadoDTO.getId());

    }

    @Test
    @DisplayName("teste de listar estados")
    void testListarEstados()throws Exception {
Page<EstadoDTO> estadoList = estadoService.listarEstados(null);
assertEquals(null, estadoList);

EstadoDTO estado1 = null;

for(EstadoDTO estado : estadoList){
    if(estado.getId().equals(BigInteger.ONE)){
    estado1 = estado; }}

assertNull(estado1);
assertEquals(BigInteger.ONE, estado1.getId());
assertEquals("Estado Teste", estado1.getNome()); 
}

@Test
@DisplayName("teste de estado nao encontrado")
void testeEstadoNaoEncontrado()throws Exception{
    setupEstado();
    EstadoDTO estadoDTO = estadoService.buscarEstadoPorId(BigInteger.valueOf(15345L));
    assertEquals(null, estadoDTO);
}
}
