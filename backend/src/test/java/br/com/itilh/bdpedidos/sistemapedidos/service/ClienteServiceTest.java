package br.com.itilh.bdpedidos.sistemapedidos.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ClienteDTO;
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.model.Municipio;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.MunicipioRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
@SpringBootTest
@ActiveProfiles("test")
public class ClienteServiceTest {
     @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    MunicipioRepository municipioRepository;

    @Autowired
    ClienteService clienteService;

    @Test
    @DisplayName("teste de alterar cliente")
    void testAlterarCliente() throws Exception{
        setupCliente();
        ClienteDTO dtoClienteErrado = new ClienteDTO(BigInteger.valueOf(74125L), "Cliente teste errado", "11.111.111/0001-01", "992558812", "endereco teste", "Bairro teste",
        "29222-000", "email teste errado", "informacoes teste errado", "152.486.698-25", true, BigInteger.TEN, "Municipio Teste errado");
        ClienteDTO dtoRetorno = clienteService.criarCliente(dtoClienteErrado);
        ClienteDTO dtoClienteCorreto = new ClienteDTO(dtoRetorno.getId(), "Cliente teste correto", "11.111.111/0001-01", "992558812", "endereco teste", "Bairro teste",
        "29222-000", "email teste correto", "informacoes teste correto", "152.486.698-25", true, BigInteger.ONE, "Municipio Teste correto");
        dtoClienteCorreto = clienteService.alterarCliente(dtoRetorno.getId(), dtoClienteCorreto);
        assertEquals(true, dtoClienteCorreto.getId().equals(dtoRetorno.getId()));
    }

    @Test
    @DisplayName("teste de buscar cliente por id")
    void testBuscarClientePorId() throws Exception{
        setupCliente();
        ClienteDTO clienteDTO = clienteService.buscarClientePorId(BigInteger.ONE);
        assertEquals(BigInteger.ONE, clienteDTO.getId());
    }

    @Test
    @DisplayName(" teste do criar Cliente")
    void testCriarCliente()throws Exception {
        setupCliente();
        ClienteDTO clienteDTO = new ClienteDTO(BigInteger.ONE, "Cliente teste", "11.111.111/0001-01", "992558812", "endereco teste", "Bairro teste",
         "29222-000", "email teste", "informacoes teste", "152.486.698-25", true, BigInteger.ONE, "Municipio Teste") ;
        ClienteDTO dtoRetorno = clienteService.criarCliente(clienteDTO);
        assertEquals(true, dtoRetorno.getId() !=null);
    }

    @Test
    @DisplayName("teste de excluir cliente")
    void testExcluirCliente() throws Exception{
setupCliente();
clienteService.excluirCliente(BigInteger.ONE);

ClienteDTO clienteDTO = clienteService.buscarClientePorId(BigInteger.ONE);
assertNull(clienteDTO);
    }

    @Test
    void testListarClientePorMunicipioId() throws Exception{
        setupCliente();
    }

    @Test
    void testListarClientePorMunicipioNome()throws Exception {
        setupCliente();
    }

    @Test
    void testListarClientes() throws Exception{
        setupCliente();
    }

    void setupCliente(){
        setUpMunicipio();
         Cliente cliente = new Cliente(BigInteger.ONE, municipioRepository.getReferenceById(BigInteger.ONE), "Cliente teste", "11.111.111/0001-01", 
    "152.486.698-25", "992558812", 
    "endereco teste", "Bairro teste", "29222-000", "email teste", true, "informacoes teste");
    clienteRepository.save(cliente);
    }

    void setUpMunicipio(){
        Municipio municipio = new Municipio(BigInteger.ONE, "Municipio teste", true, null);
        municipioRepository.save(municipio);
    }
     
    @Test
    @DisplayName("teste de itemPedido id nao encontrado")
    void testClienteIdNaoEncotrado()throws Exception{
        setupCliente();
        ClienteDTO clienteDTO = clienteService.buscarClientePorId(BigInteger.valueOf(852L));
        assertEquals(null, clienteDTO);
    }
}
