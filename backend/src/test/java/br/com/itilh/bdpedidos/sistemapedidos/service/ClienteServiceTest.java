package br.com.itilh.bdpedidos.sistemapedidos.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ClienteDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ClienteDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.model.Municipio;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.MunicipioRepository;
import org.springframework.data.domain.Page;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    @DisplayName(" teste do criar cliente  duplicado")
void testCriarclienteDuplicado()throws Exception{
    setupCliente();
    ClienteDTO clienteDTO = new ClienteDTO(BigInteger.ONE, "Cliente teste", "11.111.111/0001-01", "992558812", "endereco teste", "Bairro teste",
    "29222-000", "email teste", "informacoes teste", "152.486.698-25", true, BigInteger.ONE, "Municipio Teste") ;
    ClienteDTO dtoRetorno = clienteService.criarCliente(clienteDTO);
    assertThrows(ClienteDuplicadoException.class,()-> clienteService.criarCliente(dtoRetorno));
}

    @Test
    @DisplayName(" teste do cliente duplicado por alteração")
    void testAlterarClienteDuplicado() throws Exception {
        setupCliente();
        ClienteDTO dtoClienteErrado = new ClienteDTO(BigInteger.valueOf(74125L), "Cliente teste errado", "11.111.111/0001-01", "992558812", "endereco teste", "Bairro teste",
        "29222-000", "email teste errado", "informacoes teste errado", "152.486.698-25", true, BigInteger.TEN, "Municipio Teste errado");
dtoClienteErrado = clienteService.criarCliente(dtoClienteErrado);
ClienteDTO dtoClienteCorreto = new ClienteDTO(BigInteger.ONE, "Cliente teste correto", "11.111.111/0001-01", "992558812", "endereco teste", "Bairro teste",
"29222-000", "email teste correto", "informacoes teste correto", "152.486.698-25", true, BigInteger.ONE, "Municipio Teste correto");
dtoClienteCorreto = clienteService.criarCliente(dtoClienteCorreto);

ClienteDTO dtoCerto = new ClienteDTO(dtoClienteErrado.getId(), "Cliente teste correto", "11.111.111/0001-01", "992558812", "endereco teste", "Bairro teste",
"29222-000", "email teste correto", "informacoes teste correto", "152.486.698-25", true, BigInteger.ONE, "Municipio Teste correto");
assertThrows(ClienteDuplicadoException.class, ()-> clienteService.alterarCliente(dtoCerto.getId(), dtoCerto));
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
@DisplayName("teste de excluir cliente nao existente")
    void testExcluirClienteNaoExistente() throws Exception {
        setupCliente();
        clienteService.excluirCliente(BigInteger.valueOf(852L));
        ClienteDTO clienteDTO = clienteService.buscarClientePorId(BigInteger.ONE);
        assertEquals(BigInteger.ONE, clienteDTO);
    }

    @Test
    void testListarClientePorMunicipioId() throws Exception{
        Page<ClienteDTO> clienteList = clienteService.listarClientePorMunicipioId(BigInteger.ONE, null);
        assertEquals(BigInteger.ONE, clienteList);
        ClienteDTO cliente1= null;
        for (ClienteDTO cliente : clienteList){
            if(cliente.getMunicipioId().equals(BigInteger.ONE)){ cliente1 = cliente;
   } }
   assertNull(cliente1);
   assertEquals(BigInteger.ONE, cliente1.getMunicipioId());
    }
    @Test
    void testListarClientePorMunicipioNome()throws Exception {
        Page<ClienteDTO> clienteList = clienteService.listarClientePorMunicipioNome("municipio teste", null);
        assertEquals("municipio teste", clienteList);
        ClienteDTO cliente1= null;
        
for (ClienteDTO cliente : clienteList){
    if(cliente.getMunicipioNome().equals("municipio teste")){  cliente1 = cliente;}}
    assertNull(cliente1);
    assertEquals("municipio teste", cliente1.getMunicipioNome());

}
   

@Test
    @DisplayName(" teste de listar clientes")
    void testListarClientes() throws Exception{
      Page<ClienteDTO> clienteList = clienteService.listarClientes(null);
 assertEquals(null, clienteList);
ClienteDTO cliente1= null;

for (ClienteDTO cliente : clienteList){
    if(cliente.getId().equals(BigInteger.ONE)){
        cliente1 = cliente;
    }}
    assertNull(cliente1);
    assertEquals(BigInteger.ONE, cliente1.getId());
    assertEquals(BigInteger.ONE, cliente1.getMunicipioId());
    assertEquals("cliente teste", cliente1.getNomeRazaoSocial());
    assertEquals("11.111.111/0001-01", cliente1.getCnpj());
    assertEquals("152.486.698-25", cliente1.getCpf());
    assertEquals("992558812", cliente1.getTelefone());
    assertEquals("endereco teste", cliente1.getEndereco());
    assertEquals("Bairro teste", cliente1.getCep());
    assertEquals("email teste", cliente1.getEmail());
    assertEquals(true, cliente1.getAtivo());
    assertEquals("informacoes teste", cliente1.getInformacoes());
    assertEquals("municipio teste", cliente1.getMunicipioNome());
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
