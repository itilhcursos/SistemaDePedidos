package br.com.itilh.bdpedidos.sistemapedidos.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import br.com.itilh.bdpedidos.sistemapedidos.model.ItemPedido;
import br.com.itilh.bdpedidos.sistemapedidos.model.Pedido;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ItemPedidoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.PedidoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;
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
public class ItemPedidoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ItemPedidoRepository itemPedidoRepository;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ProdutoRepository produtoRepository;





    @Test
    void testBuscarItemPedidoPorId() {

    }

    @Test
    void testBuscarItemPedidos() {

    }

    @Test
    void testBuscarItemPedidosPorPedidoId() {

    }

    @Test
    void testBuscarItemPedidosPorProdutoId() {

    }

    @Test
    void testBuscarItemPedidosPorProdutoNome() {

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


    @Test
    void testAlterarItemPedido() {

    }

    @Test
    void testCriarItemPedido() {

    }

    @Test
    @DisplayName(" Teste do delete")
    void testDeleteItemPedido()throws Exception {
        setupItemPedido();
        mockMvc.perform( delete("/itemPedido/1")
        ).andExpect(status().isOk()
        ).andExpect(content().string(containsString("Excluído")));

    }
}
