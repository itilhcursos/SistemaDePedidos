package br.com.itilh.bdpedidos.sistemapedidos.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.dto.PedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.model.Pedido;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.PedidoRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigInteger;
import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
@SpringBootTest
@ActiveProfiles("test")
public class PedidoServiceTest {

    @Autowired
    PedidoService pedidoService;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    @Test
    @DisplayName("teste de alterar pedido")
    void testAlterarPedido() throws Exception{
        setupPedido();
        PedidoDTO dtoPedidoErrado = new PedidoDTO(BigInteger.valueOf(7798498L), Integer.valueOf(10), LocalDate.MIN,LocalDate.MIN, 
        LocalDate.MIN,BigInteger.ONE, "cliente teste errado", BigInteger.ONE, "formaPagamento errado");
    PedidoDTO dtoRetorno = pedidoService.criarPedido(dtoPedidoErrado);
        PedidoDTO dtoPedidoCorrigido = new PedidoDTO(dtoRetorno.getId(), Integer.valueOf(1), LocalDate.MIN,LocalDate.MIN, 
        LocalDate.MIN,BigInteger.ONE, "cliente corrigido", BigInteger.ONE, "formaPagamento corrigido");
        dtoPedidoCorrigido =pedidoService.alterarPedido(dtoRetorno.getId(), dtoPedidoCorrigido);
        assertEquals(true, dtoPedidoCorrigido.getId().equals(dtoRetorno.getId()));
    }

    @Test
    @DisplayName("teste de pedido id nao encontrado")
    void testPedidoIDNaoEncontrado()throws Exception{
        setupPedido();
        PedidoDTO pedidoDTO = pedidoService.buscarPedidoPorId(BigInteger.valueOf(787456L));
    assertEquals(null, pedidoDTO);
    }

    @Test
    @DisplayName("teste de buscar pedido por id")
    void testBuscarPedidoPorId() throws Exception{
        setupPedido();
        PedidoDTO pedidoDTO = pedidoService.buscarPedidoPorId(BigInteger.ONE);
        assertEquals(BigInteger.ONE, pedidoDTO);
    }

    @Test
    @DisplayName(" teste do criar Pedido")
    void testCriarPedido()throws Exception {
        setupPedido();
       PedidoDTO pedidoDTO = new PedidoDTO(BigInteger.ONE, Integer.valueOf(10), LocalDate.MIN,LocalDate.MIN, 
       LocalDate.MIN,BigInteger.ONE, "cliente teste", BigInteger.ONE, "formaPagamento teste");
       PedidoDTO dtoRetorno = pedidoService.criarPedido(pedidoDTO);
       assertEquals(true, dtoRetorno.getId() != null);
    }

    @Test
    @DisplayName("teste de excluir pedido")
    void testExcluirPedido() throws Exception{
setupPedido();
pedidoService.excluirPedido(BigInteger.ONE);
PedidoDTO pedidoDTO = pedidoService.buscarPedidoPorId(BigInteger.ONE);
assertNull(pedidoDTO);

    }

    @Test
    void testListarPedidoPorClienteId() throws Exception{
    setupPedido();
    }

    @Test
    void testListarPedidoPorClienteNome()throws Exception {
        setupPedido();
    }

    @Test
    void testListarPedidoPorFormaPagamentoId()throws Exception {
        setupPedido();
    }

    @Test
    void testListarPedidoPorFormaPagamentoNome() throws Exception{
        setupPedido();
    }

    @Test
    void testListarPedidos() throws Exception{
        setupPedido();
    }
    
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

}
