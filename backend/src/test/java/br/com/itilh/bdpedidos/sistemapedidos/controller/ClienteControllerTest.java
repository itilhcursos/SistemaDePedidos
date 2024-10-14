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
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.model.Estado;
import br.com.itilh.bdpedidos.sistemapedidos.model.Municipio;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.EstadoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.MunicipioRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ClienteControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EstadoRepository estadoRepository;
    
    @Autowired
    MunicipioRepository municipioRepository;


    void setUpCliente(){
        setUpMunicipio();
        Cliente cliente = new Cliente(
           BigInteger.ONE,
           municipioRepository.getReferenceById(BigInteger.ONE),
           "test",
           "30.829.641/0001-14",
           "111.111.111-02",
           "73988175153",
           "Rua serra capa bode",
           "Ozeias de Freitas",
           "45270-000",
           "Mikael-sl@hotmail.com",
           true,
           "ele esta bem"
        );
          clienteRepository.save(cliente);
    }

    void setUpMunicipio(){
        setUpEstado();
        Municipio municipio = new Municipio(
            BigInteger.ONE,
            "Municipio teste",
            true,
            estadoRepository.getReferenceById(BigInteger.ONE));
        municipioRepository.save(municipio);
    }

    void setUpEstado(){
        Estado estado = new Estado(BigInteger.ONE, "Estado teste");
        estadoRepository.save(estado);
    }

    @Test
    @DisplayName("testando o retorno da busca da lista de clientes pelo endPoint /clientes")
    void testGetClientes() throws Exception{

        mockMvc.perform(get("/clientes")).andExpect(status().isOk())
        .andExpect(content().string(containsString("totalElements")));

    }

    @Test
    @DisplayName("testando path inexistente")
    void testPathInexistente() throws Exception{
        mockMvc.perform(get("/cliente")).andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("testando o retorno da busca de cliente pelo Id")
    void testGetClientesPorId() throws Exception{
        setUpCliente();
        mockMvc.perform(get("/cliente/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("test")));

    }
    @Test
    @DisplayName("testando o retorno da busca de cliente pelo Id")
    void testGetClientesPorIdInexistente() throws Exception{
        setUpCliente();
        mockMvc.perform(get("/cliente/99999")).andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException() instanceof IdInexistenteException));

    }

}