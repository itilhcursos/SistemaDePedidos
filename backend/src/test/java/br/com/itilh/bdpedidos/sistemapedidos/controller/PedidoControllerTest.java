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
        Pedido pedido = new Pedido(BigInteger.ONE, null, null, Integer.valueOf(5), "01/01/2001", "02/01/2001", "01/01/2001");
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
    void testBuscarPedidoPorId() {

    }

    @Test
    void testBuscarPedidos() {

    }

    @Test
    void testBuscarPedidosPorClienteId() {

    }

    @Test
    void testBuscarPedidosPorClienteNome() {

    }

    @Test
    void testBuscarPedidosPorFormaPagamentoNome() {

    }

    @Test
    void testBuscarPedidosPorformaPagamentoId() {

    }

    @Test
    void testAlterarItemPedido() {

    }

    @Test
    void testCriarPedido() {

    }

    @Test
    @DisplayName(" Teste do delete")
    void testDeletePedido() throws Exception{
        setupPedido();
        mockMvc.perform( delete("/Pedido/1")
        ).andExpect(status().isOk()
        ).andExpect(content().string(containsString("Excluído")));

    }
}
