package br.com.itilh.bdpedidos.sistemapedidos.controller;

import java.math.BigInteger;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
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
    @DisplayName("teste de buscar cliente por id")
    void testBuscarClientePorId()throws Exception  {
setupCliente();
mockMvc.perform(get("/cliente/1")).andExpect(status().isOk())
.andExpect(content().string(containsString("1")));
    }

    @Test
    @DisplayName("teste de buscar cliente por municipio id")
    void testBuscarClientePorMunicipioId()throws Exception {
        setupCliente();
        mockMvc.perform(get("/municipio/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("1")));
    }

    @Test
    @DisplayName("teste de buscar cliente por Municipio nome")
    void testBuscarClientePorMunicipioNome()throws Exception {
        mockMvc.perform(get("/municipio/municipio teste")).andExpect(status().isOk())
        .andExpect(content().string(containsString("municipio teste")));
    
    }

    @Test
    @DisplayName("teste de buscar cliente")
    void testBuscarClientes()throws Exception  {
        setupCliente();
        mockMvc.perform(get("/clientes")).andExpect(status().isOk())
.andExpect(content().string(containsString("Cliente teste")));
    }

    @Test
     @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @DisplayName("teste de criar pedido")
    void testAlterarCliente() throws Exception{
setupCliente();
mockMvc.perform(put("/cliente/1")
.contentType("application/json")
.content("{\r\n" + //
"  \"id\": 1, \r\n" + //
" \"nomeRazaoSocial\": \"cliente teste alterado\",\r\n" + //
" \"cnpj\": \"11.222.111/0001-01\",\r\n" + //
" \"cpf\": \"152.000.698-25\",\r\n" + //
" \"telefone\": \"992589812\",\r\n" + // 
" \"endereco\": \"endereco teste alterado\",\r\n" + //
" \"bairro\": \"Bairro teste alterado\",\r\n" + //
" \"cep\": \"29112-000\",\r\n" + //
" \"email\": \"email teste alterado\",\r\n" + //
" \"ativo\": \"false\",\r\n" + //
" \"informacao\": \"informacoes teste alterado\",\r\n" + //
" \"municipio\": \"188\",\r\n" + // 
"}")
).andExpect(status().isOk())
.andExpect(content().string(containsString("cliente teste alterado")));
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @DisplayName("teste de criar produto")
    void testCriarItemPedido() throws Exception{
setupCliente();
mockMvc.perform(post("/cliente/1")
.contentType("application/json")
.content("{\r\n" + //
"  \"id\": 1, \r\n" + //
" \"nomeRazaoSocial\": \"Cliente teste\",\r\n" + //
" \"cnpj\": \"11.111.111/0001-01\",\r\n" + //
" \"cpf\": \"152.486.698-25\",\r\n" + //
" \"telefone\": \"992558812\",\r\n" + //  
" \"endereco\": \"endereco teste\",\r\n" + //
" \"bairro\": \"Bairro teste\",\r\n" + //
" \"cep\": \"29222-000\",\r\n" + //
" \"email\": \"email teste\",\r\n" + //
" \"ativo\": \"true\",\r\n" + //
" \"informacao\": \"informacoes teste\",\r\n" + //
" \"municipio\": \"1\",\r\n" + //
"}")
).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
     @DisplayName(" Teste do delete")
    void testDeleteCliente()throws Exception {
setupCliente();
mockMvc.perform( delete("/Cliente/1")
).andExpect(status().isOk()
).andExpect(content().string(containsString("Excluído")));
    }

    @Test
    @DisplayName(" Teste do path inexistente")
    void TesteGetPathInexistente() throws Exception{
setupCliente();
mockMvc.perform(get("/cliente")).andExpect(status().isMethodNotAllowed());

    }

    @Test
    @DisplayName(" Teste do path errado")
    void TesteGetPathErrado() throws Exception{
setupCliente();
mockMvc.perform(get("/clientesdasda")).andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("Teste do id existente")
    void testIdExistente() throws Exception {
        setupCliente();
        mockMvc.perform(get("/cliente/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("1")));
    }




    @Test
    @DisplayName("Teste do id inexistente")
    void TesteGetIdInexistente() throws Exception{
        setupCliente();
        mockMvc.perform(get("/cliente/65497651")).andExpect(status().isBadRequest())
        .andExpect(result -> assertTrue(result.getResolvedException()instanceof IdInexistenteException));
    }




}
