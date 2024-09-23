package br.com.itilh.bdpedidos.sistemapedidos.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ClienteDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ClienteDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Municipio;
import br.com.itilh.bdpedidos.sistemapedidos.repository.MunicipioRepository;

@SpringBootTest
@ActiveProfiles("test")
public class ClienteServiceTest {

    @Autowired
    ClienteService clienteService;

    @Autowired
    MunicipioRepository municipioRepository;

    void setUpMunicipio() {
        Municipio municipio = new Municipio(BigInteger.ONE, "Municipio Teste", null);
        municipioRepository.save(municipio);
    }

    @Test
    void testCriarCliente() throws Exception {
        setUpMunicipio();
        ClienteDTO clienteDTO = new ClienteDTO(
            null, 
            "Cliente Teste", 
            "12345678901234", 
            null, 
            "123456789", 
            "Rua Teste, 123", 
            "Bairro Teste", 
            "12345678", 
            "cliente@teste.com", 
            true, 
            "Informações do cliente", 
            BigInteger.ONE, 
            "Municipio Teste"
        );
        ClienteDTO clienteRetorno = clienteService.criarCliente(clienteDTO);
        assertEquals(true, clienteRetorno.getId() != null);
    }

    @Test
    @DisplayName("Teste de regra de negócio - Cliente Duplicado (CNPJ)")
    void testCriarClienteDuplicadoCnpj() throws Exception {
        setUpMunicipio();
        ClienteDTO clienteDTO = new ClienteDTO(
            null, 
            "Cliente Teste", 
            "12345678901234", 
            null, 
            "123456789", 
            "Rua Teste, 123", 
            "Bairro Teste", 
            "12345678", 
            "cliente@teste.com", 
            true, 
            "Informações do cliente", 
            BigInteger.ONE, 
            "Municipio Teste"
        );
        clienteService.criarCliente(clienteDTO);
        
        assertThrows(ClienteDuplicadoException.class, () -> clienteService.criarCliente(clienteDTO));
    }

    @Test
    @DisplayName("Teste de regra de negócio - Cliente Duplicado (CPF)")
    void testCriarClienteDuplicadoCpf() throws Exception {
        setUpMunicipio();
        ClienteDTO clienteDTO = new ClienteDTO(
            null, 
            "Cliente Teste", 
            null, 
            "12345678901", 
            "123456789", 
            "Rua Teste, 123", 
            "Bairro Teste", 
            "12345678", 
            "cliente@teste.com", 
            true, 
            "Informações do cliente", 
            BigInteger.ONE, 
            "Municipio Teste"
        );
        clienteService.criarCliente(clienteDTO);

        assertThrows(ClienteDuplicadoException.class, () -> clienteService.criarCliente(clienteDTO));
    }

    @Test
    @DisplayName("Teste de alteração de nome")
    void testAlterarCliente() throws Exception {
        setUpMunicipio();
        ClienteDTO clienteDTO = new ClienteDTO(
            null, 
            "Nome Incorreto", 
            "12345678901234", 
            null, 
            "123456789", 
            "Rua Teste, 123", 
            "Bairro Teste", 
            "12345678", 
            "cliente@teste.com", 
            true, 
            "Informações do cliente", 
            BigInteger.ONE, 
            "Municipio Teste"
        );
        ClienteDTO clienteRetorno = clienteService.criarCliente(clienteDTO);

        ClienteDTO clienteCorrigido = new ClienteDTO(
            clienteRetorno.getId(), 
            "Nome Correto", 
            clienteRetorno.getCnpj(), 
            clienteRetorno.getCpf(), 
            clienteRetorno.getTelefone(), 
            clienteRetorno.getEndereco(), 
            clienteRetorno.getBairro(), 
            clienteRetorno.getCep(), 
            clienteRetorno.getEmail(), 
            clienteRetorno.isAtivo(), 
            clienteRetorno.getInformacao(), 
            clienteRetorno.getMunicipioId(), 
            clienteRetorno.getMunicipioNome()
        );
        ClienteDTO clienteAtualizado = clienteService.alterarCliente(clienteRetorno.getId(), clienteCorrigido);
        assertEquals("Nome Correto", clienteAtualizado.getNomeRazaoSocial());
    }

    @Test
    @DisplayName("Teste de alteração com nome duplicado")
    void testAlterarClienteDuplicado() throws Exception {
        setUpMunicipio();
        
        ClienteDTO clienteDTO1 = new ClienteDTO(
            null, 
            "Nome Cliente 1", 
            "12345678901234", 
            null, 
            "123456789", 
            "Rua Teste, 123", 
            "Bairro Teste", 
            "12345678", 
            "cliente1@teste.com", 
            true, 
            "Informações do cliente 1", 
            BigInteger.ONE, 
            "Municipio Teste"
        );
        clienteService.criarCliente(clienteDTO1);

        ClienteDTO clienteDTO2 = new ClienteDTO(
            null, 
            "Nome Cliente 2", 
            "98765432109876", 
            null, 
            "987654321", 
            "Rua Teste, 456", 
            "Bairro Teste", 
            "87654321", 
            "cliente2@teste.com", 
            true, 
            "Informações do cliente 2", 
            BigInteger.ONE, 
            "Municipio Teste"
        );
        ClienteDTO clienteRetorno = clienteService.criarCliente(clienteDTO2);

        ClienteDTO clienteDuplicado = new ClienteDTO(
            clienteRetorno.getId(), 
            "Nome Cliente 1", 
            clienteRetorno.getCnpj(), 
            clienteRetorno.getCpf(), 
            clienteRetorno.getTelefone(), 
            clienteRetorno.getEndereco(), 
            clienteRetorno.getBairro(), 
            clienteRetorno.getCep(), 
            clienteRetorno.getEmail(), 
            clienteRetorno.isAtivo(), 
            clienteRetorno.getInformacao(), 
            clienteRetorno.getMunicipioId(), 
            clienteRetorno.getMunicipioNome()
        );

        assertThrows(ClienteDuplicadoException.class, () -> clienteService.alterarCliente(clienteRetorno.getId(), clienteDuplicado));
    }
}
