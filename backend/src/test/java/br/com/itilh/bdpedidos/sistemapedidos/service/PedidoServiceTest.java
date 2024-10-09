package br.com.itilh.bdpedidos.sistemapedidos.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.dto.PedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.PedidoDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.model.ItemPedido;
import br.com.itilh.bdpedidos.sistemapedidos.model.Pedido;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ItemPedidoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.PedidoRepository;
import org.springframework.data.domain.Page;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
@SpringBootTest
@ActiveProfiles("test")
public class PedidoServiceTest {

    @Autowired
    PedidoService pedidoService;

    @Autowired
    ItemPedidoRepository itemPedidoRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;


    @Test
    @DisplayName(" teste do pedido duplicado por alteração")
    void testAlterarPedidoDuplicado() throws Exception {
setupPedido();
PedidoDTO dtoPedidoErrado = new PedidoDTO(BigInteger.valueOf(7798498L), Integer.valueOf(10), LocalDate.MIN,LocalDate.MIN, 
LocalDate.MIN,null, BigInteger.ONE, "cliente teste errado", BigInteger.ONE, "formaPagamento errado");
dtoPedidoErrado = pedidoService.criarPedido(dtoPedidoErrado);
PedidoDTO dtoPedidoCorrigido = new PedidoDTO(BigInteger.ONE, Integer.valueOf(1), LocalDate.MIN,LocalDate.MIN, 
LocalDate.MIN,null,BigInteger.ONE, "cliente corrigido", BigInteger.ONE, "formaPagamento corrigido");
dtoPedidoCorrigido = pedidoService.criarPedido(dtoPedidoCorrigido);

PedidoDTO dtoReparado = new PedidoDTO(dtoPedidoErrado.getId(), Integer.valueOf(1), LocalDate.MIN,LocalDate.MIN, 
LocalDate.MIN,null,BigInteger.ONE, "cliente corrigido", BigInteger.ONE, "formaPagamento corrigido");
assertThrows(PedidoDuplicadoException.class,()-> pedidoService.alterarPedido(dtoReparado.getId(), dtoReparado));
    }

    
    @Test
    @DisplayName("teste de alterar pedido")
    void testAlterarPedido() throws Exception{
        setupPedido();
        PedidoDTO dtoPedidoErrado = new PedidoDTO(BigInteger.valueOf(7798498L), Integer.valueOf(10), LocalDate.MIN,LocalDate.MIN, 
        LocalDate.MIN,null,BigInteger.ONE, "cliente teste errado", BigInteger.ONE, "formaPagamento errado");
    PedidoDTO dtoRetorno = pedidoService.criarPedido(dtoPedidoErrado);
        PedidoDTO dtoPedidoCorrigido = new PedidoDTO(dtoRetorno.getId(), Integer.valueOf(1), LocalDate.MIN,LocalDate.MIN, 
        LocalDate.MIN,null,BigInteger.ONE, "cliente corrigido", BigInteger.ONE, "formaPagamento corrigido");
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
       LocalDate.MIN,null,BigInteger.ONE, "cliente teste", BigInteger.ONE, "formaPagamento teste");
       PedidoDTO dtoRetorno = pedidoService.criarPedido(pedidoDTO);
       assertEquals(true, dtoRetorno.getId() != null);
    }


    @Test
    @DisplayName(" teste do criar pedido duplicado")
void testCriarPedidoDuplicado()throws Exception{
    setupPedido();
    PedidoDTO pedidoDTO = new PedidoDTO(BigInteger.ONE, Integer.valueOf(10), LocalDate.MIN,LocalDate.MIN, 
    LocalDate.MIN,null,BigInteger.ONE, "cliente teste", BigInteger.ONE, "formaPagamento teste");
    PedidoDTO  dtoRetorno = pedidoService.criarPedido(pedidoDTO);
    assertThrows(PedidoDuplicadoException.class,()-> pedidoService.criarPedido(dtoRetorno));
}

@Test
@DisplayName("teste de excluir pedido nao existente")
    void testExcluirPedidoNaoExistente() throws Exception {
        setupPedido();
        pedidoService.excluirPedido(BigInteger.valueOf(7411L));
        PedidoDTO pedidoDTO = pedidoService.buscarPedidoPorId(BigInteger.ONE);
        assertEquals(BigInteger.ONE, pedidoDTO);
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
    @DisplayName("teste de listar pedido por cliente id")
    void testListarPedidoPorClienteId() throws Exception{
        Page<PedidoDTO> pedidoList = pedidoService.listarPedidoPorClienteId(BigInteger.ONE, null);
     assertEquals(BigInteger.ONE, pedidoList);
     PedidoDTO pedido1 =null;
     for(PedidoDTO pedido : pedidoList){
      if(pedido.getClienteId().equals(BigInteger.ONE)){
        pedido1 = pedido;
      }
    }
    assertNull(pedido1);
    assertEquals(BigInteger.ONE, pedido1.getClienteId());
}
    @Test
    @DisplayName("teste de listar Pedido por cliente nome")
    void testListarPedidoPorClienteNome()throws Exception {
        Page<PedidoDTO> pedidoList = pedidoService.listarPedidoPorClienteNome("cliente teste", null);
        assertEquals("cliente teste", pedidoList);
        PedidoDTO pedido1 =null;
        for(PedidoDTO pedido : pedidoList){
         if(pedido.getClienteNome().equals("cliente teste")){
            pedido1 = pedido; }}
            assertNull(pedido1);
            assertEquals("cliente teste", pedido1.getClienteNome());
        }
    @Test
    @DisplayName("teste de listar edido porformaPagamento id")
    void testListarPedidoPorFormaPagamentoId()throws Exception {
      
        Page<PedidoDTO> pedidoList = pedidoService.listarPedidoPorFormaPagamentoId(BigInteger.ONE, null);
        assertEquals(BigInteger.ONE, pedidoList);
        PedidoDTO pedido1 =null;
        for(PedidoDTO pedido : pedidoList){
         if(pedido.getFormaPagamentoId().equals(BigInteger.ONE)){
            pedido1 = pedido;
         }}
    assertNull(pedido1);
    assertEquals(BigInteger.ONE, pedido1.getFormaPagamentoId());
}

    @Test
    @DisplayName("teste de listar Pedido por formaPagamento nome")
    void testListarPedidoPorFormaPagamentoDescricao() throws Exception{
        Page<PedidoDTO> pedidoList = pedidoService.listarPedidoPorFormaPagamentoNome("formaPagamento teste", null);
        assertEquals("formaPagamento teste", pedidoList);
        PedidoDTO pedido1 =null;
        for(PedidoDTO pedido : pedidoList){
         if(pedido.getFormaPagamentoDescricao().equals("formaPagamento teste")){
         pedido1 = pedido; }  }
         assertNull(pedido1);
         assertEquals("formaPagamento teste", pedido1.getFormaPagamentoDescricao());
        }

    @Test
    @DisplayName(" teste de listar pedidos")
    void testListarPedidos() throws Exception{
       Page<PedidoDTO> pedidoList = pedidoService.listarPedidos(null);

       assertEquals(null, pedidoList);

       PedidoDTO pedido1 =null;
       for(PedidoDTO pedido : pedidoList){
        if(pedido.getId().equals(BigInteger.ONE)){
            pedido1 = pedido;  }}
            assertNull(pedido1);
            assertEquals(BigInteger.ONE, pedido1.getId());
            assertEquals(BigInteger.ONE, pedido1.getClienteId());
            assertEquals(BigInteger.ONE, pedido1.getFormaPagamentoId());
            assertEquals(null, pedido1.getItens());
            assertEquals("cliente nome", pedido1.getClienteNome());
            assertEquals("formaPagamento teste", pedido1.getFormaPagamentoDescricao());
            assertEquals( Integer.valueOf(5), pedido1.getNumero());
            assertEquals(LocalDate.MIN, pedido1.getCompra());
            assertEquals(LocalDate.MIN, pedido1.getEntrega());
            assertEquals(LocalDate.MIN, pedido1.getPagamento());
        }
        @Test
        @DisplayName("teste de listar Pedido por Itens")
        void testListarPedidoPorItens() throws Exception{
            Page<PedidoDTO> pedidoList = pedidoService.listarPedidoPorItens(null, null);
            assertEquals(null, pedidoList);

            PedidoDTO pedido1 =null;
            for(PedidoDTO pedido : pedidoList){
             if(pedido.getItens().equals(null)){
                pedido1 = pedido; 
             } }
             assertNull(pedido1);
             assertEquals(null, pedido1.getItens());
            }
    
void setupPedido(){
    setupCliente();
    setupFormaPagamento();
        Pedido pedido = new Pedido(BigInteger.ONE, clienteRepository.getReferenceById(BigInteger.ONE), formaPagamentoRepository.getReferenceById(BigInteger.ONE), 
        Integer.valueOf(5), null , null, null);
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
