package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.model.Estado;
import br.com.itilh.bdpedidos.sistemapedidos.model.Municipio;
import br.com.itilh.bdpedidos.sistemapedidos.repository.EstadoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.MunicipioRepository;

@SpringBootTest
@ActiveProfiles("test")
public class ClienteServiceTest {

    @Autowired
    ClienteService service;

    @Autowired
    MunicipioRepository municipioRepository;

    @Autowired
    EstadoRepository estadoRepository;


    void setUpMunicipio(){
       setUpEstado();
       Municipio municipio = new Municipio(BigInteger.ONE, "teste", true, estadoRepository.getReferenceById(BigInteger.ONE) );
       municipioRepository.save(municipio);
    }

    void setUpEstado(){
        Estado estado = new Estado(BigInteger.ONE, "Estado teste");
        estadoRepository.save(estado);
    }

    // @Test
    // @DisplayName("Test de criação de um cliente")
    // void testCriarCliente() throws Exception {
    //     setUpMunicipio();
    //     ClienteDTO testeDTO = new ClienteDTO(null,  
    //     municipioRepository.getReferenceById(BigInteger.ONE),
    //     "test"   

        
    //     );
    // }

    // @Test
    // @DisplayName("Test de criação de um estado duplicado")
    // void testEstadoDuplicado() throws Exception {

    //     EstadoDTO testeDTO = new EstadoDTO(null, "estado de teste duplicado");

    //     EstadoDTO retono = service.criarEstado(testeDTO);
    //     assertThrows(EstadoDuplicadoException.class, ()-> service.criarEstado(testeDTO));
    // }
}
