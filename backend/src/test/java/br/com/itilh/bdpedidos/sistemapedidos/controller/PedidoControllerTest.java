package br.com.itilh.bdpedidos.sistemapedidos.controller;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
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

import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.model.Estado;
import br.com.itilh.bdpedidos.sistemapedidos.model.ItemPedido;
import br.com.itilh.bdpedidos.sistemapedidos.model.Municipio;
import br.com.itilh.bdpedidos.sistemapedidos.model.Pedido;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.EstadoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.MunicipioRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.PedidoRepository;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PedidoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    MunicipioRepository municipioRepository;

    @Autowired
    EstadoRepository estadoRepository;


    void setUpPedido(){
        List<ItemPedido>itens = new ArrayList<ItemPedido>();
        // ItemPedido item1 = new ItemPedido();
        Pedido p1 = new Pedido(
            BigInteger.ONE,
            clienteRepository.getReferenceById(BigInteger.ONE),
            formaPagamentoRepository.getReferenceById(BigInteger.ONE),
            BigInteger.ONE,
            LocalDate.of(2020, 1, 8),
            LocalDate.of(2020, 1, 8),
            LocalDate.of(2020, 1, 8),
            itens
            
        );
        pedidoRepository.save(p1);
    }

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
    @DisplayName("testando o retorno da busca da lista de pedidos pelo endPoint /pedidos")
    void testGetPedidos() throws Exception{

        mockMvc.perform(get("/pedidos")).andExpect(status().isOk())
        .andExpect(content().string(containsString("totalElements")));

    }
    @Test
    @DisplayName("testando path inexistente")
    void testPathInexistente() throws Exception{
        mockMvc.perform(get("/pedido")).andExpect(status().isMethodNotAllowed());
    }

    @Test
    @DisplayName("testando o retorno da busca de cliente pelo Id")
    void testGetPedidoPorId() throws Exception{
        setUpPedido();
        mockMvc.perform(get("/pedido/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("18/08/18")));

    }
}
