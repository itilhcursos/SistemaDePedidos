package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.dto.MunicipioDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.MunicipioDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Estado;
import br.com.itilh.bdpedidos.sistemapedidos.repository.EstadoRepository;


@SpringBootTest
@ActiveProfiles("test")
public class MunicipioServiceTest {

    @Autowired
    MunicipioService municipioService;

    @Autowired
    EstadoRepository estadoRepository;

    void setUpEstado(){
        Estado estado = new Estado(BigInteger.ONE, "Estado teste");
        estadoRepository.save(estado);
    }

    @Test
    void testCriarMunicipio() throws Exception {
        setUpEstado();
        MunicipioDTO dto = new MunicipioDTO(null,"Municipio teste",true, BigInteger.ONE, "estado teste");
        MunicipioDTO dtoRetorno = municipioService.criarMunicipio(dto);
        assertEquals(true, dtoRetorno.getId() != null);
    }

    @Test
    @DisplayName("Teste de regra de negócio -  Município Duplicado")
    void testCriarMunicipioDuplicado() throws Exception {
        setUpEstado();
        MunicipioDTO dto = new MunicipioDTO(null,"Municipio teste duplicado",true, BigInteger.ONE, "estado teste");
        MunicipioDTO dtoRetorno = municipioService.criarMunicipio(dto);
        
        assertThrows(MunicipioDuplicadoException.class, ()-> municipioService.criarMunicipio(dto));
    }

    @Test
    @DisplayName("Teste de alteração de nome")
    void testCriarMunicipioAlterar() throws Exception {
        setUpEstado();
        MunicipioDTO dtoNomeErrado = new MunicipioDTO(null,"Nome errado",true, BigInteger.ONE, "estado teste");
        MunicipioDTO dtoRetorno = municipioService.criarMunicipio(dtoNomeErrado);

        MunicipioDTO dtoNomeCorrigido = new MunicipioDTO(dtoRetorno.getId(),"Nome corrigido",true, BigInteger.ONE, "estado teste");
        dtoNomeCorrigido = municipioService.alterarMunicipio(dtoRetorno.getId(), dtoNomeCorrigido);
        assertEquals(true, dtoNomeCorrigido.getId().equals(dtoRetorno.getId()));
    }

    @Test
    @DisplayName("Teste de alteração de nome duplicado")
    void testCriarMunicipioDuplicadoAlterar() throws Exception {
        setUpEstado();
        // criar municipio errado
        MunicipioDTO dtoNomeErrado = new MunicipioDTO(null,"Nome errado",true, BigInteger.ONE, "estado teste");
        dtoNomeErrado = municipioService.criarMunicipio(dtoNomeErrado);
        //criar municicipio correto
        MunicipioDTO dtoCorrigido = new MunicipioDTO(null,"Nome corrigido",true, BigInteger.ONE, "estado teste");
        dtoCorrigido = municipioService.criarMunicipio(dtoCorrigido);

        // gerar erro ao tentar mudar o nome de um municipio para outro já existente
        MunicipioDTO dtoNomeCorrigido = new MunicipioDTO(dtoNomeErrado.getId(),"Nome corrigido",true, BigInteger.ONE, "estado teste");
        assertThrows(MunicipioDuplicadoException.class, ()-> municipioService.alterarMunicipio(dtoNomeCorrigido.getId(), dtoNomeCorrigido));
    }
    



}
