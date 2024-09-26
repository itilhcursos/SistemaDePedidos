package br.com.itilh.bdpedidos.sistemapedidos.controller;

import java.math.BigInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.model.Pedido;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.PedidoRepository;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc 
@ActiveProfiles("test") 
public class PedidoControllerTest {

  @Autowired
    MockMvc mockMvc;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;



void setupPedido(){
    setupCliente();
    setupFormaPagamento();
        Pedido pedido = new Pedido(BigInteger.ONE, clienteRepository.getReferenceById(BigInteger.ONE), formaPagamentoRepository.getReferenceById(BigInteger.ONE), 
        Integer.valueOf(5), "01/01/2001", "02/01/2001", "01/01/2001");
        pedidoRepository.save(pedido);
}

void setupFormaPagamento(){
    FormaPagamento formaPagamento = new FormaPagamento(BigInteger.ONE, "Forma Pagamento teste", true);
    formaPagamentoRepository.save(formaPagamento);
}

void setupCliente(){

    Cliente cliente = new Cliente(BigInteger.ONE, null, "Cliente teste", "11.111.111/0001-01", "152.486.698-25", "992558812", 
    "endereço teste", "Bairro teste", "29222-000", "email teste", true, "informacoes teste");
    clienteRepository.save(cliente);
}

    @Test
    @DisplayName("teste de buscar pedido por id")
    void testBuscarPedidoPorId() throws Exception{
setupPedido();
mockMvc.perform(get("/pedido/1")).andExpect(status().isOk())
.andExpect(content().string(containsString("1")));
    }

    @Test
    void testBuscarPedidos() throws Exception{
        setupPedido();
        mockMvc.perform(get("/pedidos")).andExpect(status().isOk())
        .andExpect(content().string(containsString(" cliente")));
    }

    @Test
    void testBuscarPedidosPorClienteId() throws Exception{
        setupPedido();
        mockMvc.perform(get("/cliente/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("1")));
    }

    @Test
    void testBuscarPedidosPorClienteNome()throws Exception {
        setupPedido();
        mockMvc.perform(get("/cliente/Cliente teste")).andExpect(status().isOk())
        .andExpect(content().string(containsString("Cliente teste")));
    }

    @Test
    void testBuscarPedidosPorFormaPagamentoNome()throws Exception {
        setupPedido();
        mockMvc.perform(get("/formas-pagamento/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("1")));
    
    }

    @Test
    void testBuscarPedidosPorformaPagamentoId()throws Exception {
        setupPedido();
        mockMvc.perform(get("/fomas-pagamento/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("1")));
    
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @DisplayName("teste de criar pedido")
    void testAlterarPedido() throws Exception{
    setupPedido();
    mockMvc.perform(put("/pedido/1")
    .contentType("application/json")
    .content("{\r\n" + //
    "  \"id\": 1, \r\n" + //
    " \"cliente\": \"25\",\r\n" + //
    " \"numero\": \"98\",\r\n" + //
    " \"compra\": \"18/48/2001\",\r\n" + //
    " \"entrega\": \"10/10/2100\",\r\n" + //  
    " \"pagamento\": \"78/18/1297\",\r\n" + //  
    " \"formaPagamento\": \"5089\",\r\n" + //  
    "}")
    ).andExpect(status().isOk())
    .andExpect(content().string(containsString("25")));
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @DisplayName("teste de criar pedido")
    void testCriarPedido()throws Exception {
        setupPedido();
        mockMvc.perform(post("/pedido/1")
        .contentType("application/json")
        .content("{\r\n" + //
        "  \"id\": 1, \r\n" + //
        " \"cliente\": \"1\",\r\n" + //
        " \"numero\": \"5\",\r\n" + //
        " \"compra\": \"01/01/2001\",\r\n" + //
        " \"entrega\": \"02/01/2001\",\r\n" + //  
        " \"pagamento\": \"01/01/2001\",\r\n" + //  
        " \"formaPagamento\": \"1\",\r\n" + //  
        "}")
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @DisplayName(" Teste do delete")
    void testDeletePedido() throws Exception{
        setupPedido();
        mockMvc.perform( delete("/Pedido/1")
        ).andExpect(status().isOk()
        ).andExpect(content().string(containsString("Excluído")));

    }


    @Test
    @DisplayName(" Teste do path inexistente")
    void TesteGetPathInexistente() throws Exception{
        setupPedido();
        mockMvc.perform(get("/pedido")).andExpect(status().isMethodNotAllowed());
    }

    
    @Test
    @DisplayName(" Teste do path errado")
    void TesteGetPathErrado() throws Exception{
        setupPedido();
        mockMvc.perform(get("/peidasf")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Teste do id existente")
    void testIdExistente() throws Exception {
        setupPedido();
        mockMvc.perform(get("/pedido/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("1")));
    }



    @Test
    @DisplayName("Teste do id inexistente")
    void TesteGetIdInexistente() throws Exception{
        setupPedido();
         mockMvc.perform(get("/pedido/2065184")).andExpect(status().isBadRequest())
         .andExpect(result -> assertTrue(result.getResolvedException()instanceof IdInexistenteException));
    }

}
