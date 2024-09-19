package br.com.itilh.bdpedidos.sistemapedidos.controller;

import java.math.BigInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.model.Municipio;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;
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
    MunicipioRepository municipioRepository;


    void setupCliente(){
        setUpMunicipio();
         Cliente cliente = new Cliente(BigInteger.ONE, null, "Cliente teste", "11.111.111/0001-01", "152.486.698-25", "992558812", 
    "endereço teste", "Bairro teste", "29222-000", "email teste", true, "informacoes teste");
    clienteRepository.save(cliente);
    }

    void setUpMunicipio(){
        Municipio municipio = new Municipio(BigInteger.ONE, "Municipio teste", true, null);
        municipioRepository.save(municipio);
    }

    @Test
    void testBuscarClientePorId() {

    }

    @Test
    void testBuscarClientePorMunicipioId() {

    }

    @Test
    void testBuscarClientePorMunicipioNome() {

    }

    @Test
    void testBuscarClientes() {

    }

    @Test
    void testAlterarCliente() {

    }

    @Test
    void testCriarItemPedido() {

    }

    @Test
     @DisplayName(" Teste do delete")
    void testDeleteCliente()throws Exception {
setupCliente();
mockMvc.perform( delete("/Cliente/1")
).andExpect(status().isOk()
).andExpect(content().string(containsString("Excluído")));
    }
}
