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

import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
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
    @DisplayName("teste de buscar ItemPedido por id")
    void testBuscarItemPedidoPorId()throws Exception {
setupItemPedido();
mockMvc.perform(get("/ItemPedido/1")).andExpect(status().isOk())
.andExpect(content().string(containsString("1")));
    }

    @Test
    @DisplayName("teste de buscar ItemPedido ")
    void testBuscarItemPedidos()throws Exception  {
        setupItemPedido();
        
        mockMvc.perform(get("/ItemPedidos")).andExpect(status().isOk())
        .andExpect(content().string(containsString(" produto")));
    }

    @Test
    @DisplayName("teste de buscar ItemPedido por pedido id ")
    void testBuscarItemPedidosPorPedidoId()throws Exception  {
        setupItemPedido();
        mockMvc.perform(get("/pedido/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("1")));
    }

    @Test
    @DisplayName("teste de buscar ItemPedido por produto id ")
    void testBuscarItemPedidosPorProdutoId()throws Exception  {
        setupItemPedido();

        mockMvc.perform(get("/produto/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("1")));
    }

    @Test
    @DisplayName("teste de buscar ItemPedido por produto nome ")
    void testBuscarItemPedidosPorProdutoNome()throws Exception  {
        setupItemPedido();
        mockMvc.perform(get("/produto/produto teste")).andExpect(status().isOk())
        .andExpect(content().string(containsString("produto teste")));
    }

    void setupItemPedido(){
       setupPedido();
       setupProduto();
ItemPedido itemPedido = new ItemPedido(BigInteger.ONE,pedidoRepository.getReferenceById(BigInteger.ONE),
produtoRepository.getReferenceById(BigInteger.ONE), Double.valueOf(1L), BigDecimal.valueOf(5L));
    }

    void setupPedido(){

        Pedido pedido = new Pedido(BigInteger.ONE, null, null, Integer.valueOf(5), null, null, null);
        pedidoRepository.save(pedido);
    }

void setupProduto(){

       Produto produto = new Produto(BigInteger.ONE, "produto teste",Double.valueOf(1), BigDecimal.valueOf(1), true);
        produtoRepository.save(produto);
}


    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @DisplayName("teste de alterar itemPedido")
    void testAlterarItemPedido()throws Exception  {
    setupItemPedido();
    mockMvc.perform(put("/ItemPedido/1")
    .contentType("application/json")
    .content("{\r\n" + //
    "  \"id\": 1, \r\n" + //
    " \"pedido\": \"2\",\r\n" + //
    " \"quantidadeEstoque\": \"489\",\r\n" + //
    " \"precoUnidadeAtual\": \"789\",\r\n" + //
    " \"produto\": \"50\",\r\n" + //  
    "}")
    ).andExpect(status().isOk())
    .andExpect(content().string(containsString("489")));
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @DisplayName("teste de criar itemPedido")
    void testCriarItemPedido()throws Exception  {
        setupItemPedido();
        mockMvc.perform(post("/ItemPedido/1")
        .contentType("application/json")
        .content("{\r\n" + //
        "  \"id\": 1, \r\n" + //
        " \"pedido\": \"1\",\r\n" + //
        " \"quantidadeEstoque\": \"1\",\r\n" + //
        " \"precoUnidadeAtual\": \"1\",\r\n" + //
        " \"produto\": \"1\",\r\n" + //  
        "}")
        ).andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    @DisplayName(" Teste do delete")
    void testDeleteItemPedido()throws Exception {
        setupItemPedido();
        mockMvc.perform( delete("/itemPedido/1")
        ).andExpect(status().isOk()
        ).andExpect(content().string(containsString("Excluído")));

    }

    
    @Test
    @DisplayName(" Teste do path inexistente")
    void TesteGetPathInexistente() throws Exception{
        setupItemPedido();
        mockMvc.perform(get("/ItemPedido")).andExpect(status().isMethodNotAllowed());
    }

    
    @Test
    @DisplayName(" Teste do path errado")
    void TesteGetPathErrado() throws Exception{
        setupItemPedido();
        mockMvc.perform(get("/Itempedisao")).andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Teste do id existente")
    void testIdExistente() throws Exception {
        setupItemPedido();
        mockMvc.perform(get("/ItemPedido/1")).andExpect(status().isOk())
        .andExpect(content().string(containsString("1")));
    }


    @Test
    @DisplayName("Teste do id inexistente")
    void TesteGetIdInexistente() throws Exception{
        setupItemPedido();
         mockMvc.perform(get("/ItemPedido/684135841")).andExpect(status().isBadRequest())
         .andExpect(result -> assertTrue(result.getResolvedException()instanceof IdInexistenteException));
    }



}
