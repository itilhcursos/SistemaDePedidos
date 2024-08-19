package br.com.itilh.bdpedidos.sistemapedidos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;

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


    private void setupEstado() {
        Estado estado = new Estado();
        estado.setId(BigInteger.ONE);
        estado.setNome("estado Teste");
        this.estadoRepository.save(estado);
    }

    @Test
    void testCriarMunicipio() throws Exception {
        setupEstado();
        MunicipioDTO dto = new MunicipioDTO();
        dto.setEstadoNome("Teste Municipio");
        dto.setEntrega(true);
        dto.setEstadoId(BigInteger.ONE);
        dto.setEstadoNome("estado Teste");

        MunicipioDTO result = municipioService.criarMunicipio(dto);
        assertEquals(true, result.getId() != null);

    }

    @Test
    void testCriarMunicipioDuplicado() throws Exception {
        setupEstado();
        MunicipioDTO dto = new MunicipioDTO();
        dto.setEstadoNome("Teste Municipio");
        dto.setEntrega(true);
        dto.setEstadoId(BigInteger.ONE);
        dto.setEstadoNome("estado Teste");
        
        MunicipioDTO result = municipioService.criarMunicipio(dto);
        assertThrows(MunicipioDuplicadoException.class, ()->municipioService.criarMunicipio(dto));

    }
}
