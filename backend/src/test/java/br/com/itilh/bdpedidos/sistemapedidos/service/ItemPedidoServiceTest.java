package br.com.itilh.bdpedidos.sistemapedidos.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ItemPedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.model.ItemPedido;
import br.com.itilh.bdpedidos.sistemapedidos.model.Pedido;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ItemPedidoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.PedidoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
@SpringBootTest
@ActiveProfiles("test")
public class ItemPedidoServiceTest {


    @Autowired
    ItemPedidoService itemPedidoService;
    
    @Autowired
    ItemPedidoRepository itemPedidoRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ProdutoRepository produtoRepository;


    @Test
    @DisplayName("teste de alterar itemPedido")
    void testAlterarItemPedido()throws Exception {
        setupItemPedido();
        ItemPedidoDTO dtoitemPedidoErrado = new ItemPedidoDTO(BigInteger.TEN, Double.valueOf(48489498L), 
        BigDecimal.valueOf(10L), BigInteger.ONE, BigInteger.ONE, "produt errado");
        ItemPedidoDTO dtoRetorno = itemPedidoService.criarItemPedido(dtoitemPedidoErrado);
        ItemPedidoDTO dtoitemPedidoCerto = new ItemPedidoDTO(dtoRetorno.getId(), Double.valueOf(1L), 
        BigDecimal.valueOf(10L), BigInteger.ONE, BigInteger.ONE, "produt certo");
       dtoitemPedidoCerto = itemPedidoService.alterarItemPedido(dtoRetorno.getId(), dtoitemPedidoCerto);
       assertEquals(true,dtoitemPedidoCerto.getId().equals(dtoRetorno.getId()));
    }

    @Test
    @DisplayName("teste de buscar itemPedido por id")
    void testBuscarItemPedidoPorId()throws Exception {
        setupItemPedido();
        ItemPedidoDTO itemPedidoDTO = itemPedidoService.buscarItemPedidoPorId(BigInteger.ONE);
        assertEquals(BigInteger.ONE, itemPedidoDTO);
    }

    @Test
    @DisplayName(" teste do criar itemPedido")
    void testCriarItemPedido()throws Exception {
        setupItemPedido();
        ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO(BigInteger.ONE, Double.valueOf(5L), 
        BigDecimal.valueOf(10L), BigInteger.ONE, BigInteger.ONE, "produt teste");
        ItemPedidoDTO dtoRetorno = itemPedidoService.criarItemPedido(itemPedidoDTO);
        assertEquals(true, dtoRetorno.getId() !=null);
    }

    @Test
    @DisplayName("teste de excluir itemPedido")
    void testExcluirItemPedido() throws Exception{
setupItemPedido();
itemPedidoService.excluirItemPedido(BigInteger.ONE);

ItemPedidoDTO itemPedidoDTO = itemPedidoService.buscarItemPedidoPorId(BigInteger.ONE);
assertNull(itemPedidoDTO);
    }

    @Test
    void testListarItemPedidoPorPedidoId() throws Exception{
        setupItemPedido();
    }

    @Test
    void testListarItemPedidoPorProdutoId()throws Exception {
        setupItemPedido();
    }

    @Test
    void testListarItemPedidoPorProdutoNome() throws Exception{
        setupItemPedido();
    }

    @Test
    void testListarItemPedidos() throws Exception{
        setupItemPedido();
    }
    
    @Test
    @DisplayName("teste de itemPedido id nao encontrado")
void testItemPedidoIdNaoEncontrado() throws Exception{
    setupItemPedido();
    ItemPedidoDTO itemPedidoDTO = itemPedidoService.buscarItemPedidoPorId(BigInteger.valueOf(6541L));
    assertEquals(null, itemPedidoDTO);
}

       void setupItemPedido(){
       setupPedido();
       setupProduto();
ItemPedido itemPedido = new ItemPedido(BigInteger.ONE,pedidoRepository.getReferenceById(BigInteger.ONE),
produtoRepository.getReferenceById(BigInteger.ONE), Double.valueOf(1L), BigDecimal.valueOf(5L));
    }

    void setupPedido(){

        Pedido pedido = new Pedido(BigInteger.ONE, null, null, Integer.valueOf(5), "01/01/2001", "02/01/2001", "01/01/2001");
        pedidoRepository.save(pedido);
    }

void setupProduto(){

       Produto produto = new Produto(BigInteger.ONE, "produto teste",Double.valueOf(1), BigDecimal.valueOf(1), true);
        produtoRepository.save(produto);
}
}
