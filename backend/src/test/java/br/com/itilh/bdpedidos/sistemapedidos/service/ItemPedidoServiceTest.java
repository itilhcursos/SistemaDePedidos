package br.com.itilh.bdpedidos.sistemapedidos.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.data.domain.Page;
import br.com.itilh.bdpedidos.sistemapedidos.dto.ItemPedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ItemPedidoDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ItemPedidoEstoqueNegativoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ItemPedidoPrecoNegativoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.ItemPedido;
import br.com.itilh.bdpedidos.sistemapedidos.model.Pedido;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ItemPedidoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.PedidoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        BigDecimal.valueOf(10L), BigInteger.ONE, BigInteger.ONE, "produto errado");
        ItemPedidoDTO dtoRetorno = itemPedidoService.criarItemPedido(dtoitemPedidoErrado);
        ItemPedidoDTO dtoitemPedidoCerto = new ItemPedidoDTO(dtoRetorno.getId(), Double.valueOf(1L), 
        BigDecimal.valueOf(10L), BigInteger.ONE, BigInteger.ONE, "produt certo");
       dtoitemPedidoCerto = itemPedidoService.alterarItemPedido(dtoRetorno.getId(), dtoitemPedidoCerto);
       assertEquals(true,dtoitemPedidoCerto.getId().equals(dtoRetorno.getId()));
    }
    @Test
    @DisplayName(" teste do itemPedido duplicado por alteração")
    void testAlterarItemPedidoDuplicado() throws Exception {
setupItemPedido();
ItemPedidoDTO dtoitemPedidoErrado = new ItemPedidoDTO(BigInteger.TEN, Double.valueOf(48489498L), 
BigDecimal.valueOf(10L), BigInteger.ONE, BigInteger.ONE, "produto errado");
dtoitemPedidoErrado = itemPedidoService.criarItemPedido(dtoitemPedidoErrado);

ItemPedidoDTO dtoitemPedidoCerto = new ItemPedidoDTO(BigInteger.ONE, Double.valueOf(1L), 
BigDecimal.valueOf(10L), BigInteger.ONE, BigInteger.ONE, "produt certo");
dtoitemPedidoCerto = itemPedidoService.criarItemPedido(dtoitemPedidoCerto);

ItemPedidoDTO dtoCorrigido = new ItemPedidoDTO(dtoitemPedidoErrado.getId(), Double.valueOf(1L), 
BigDecimal.valueOf(10L), BigInteger.ONE, BigInteger.ONE, "produto certo");

assertThrows(ItemPedidoDuplicadoException.class, ()-> itemPedidoService.alterarItemPedido(dtoCorrigido.getId(), dtoCorrigido));
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
    @DisplayName(" teste do criar itemPedido  duplicado")
void testCriarItemPedidoDuplicado()throws Exception{
    setupItemPedido();
    ItemPedidoDTO dto = new ItemPedidoDTO(BigInteger.TEN, Double.valueOf(48489498L), 
    BigDecimal.valueOf(10L), BigInteger.ONE, BigInteger.ONE, "produto teste duplucado");
    ItemPedidoDTO dtoRetorno = itemPedidoService.criarItemPedido(dto);

    assertThrows(ItemPedidoDuplicadoException.class, ()-> itemPedidoService.criarItemPedido(dto));
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
@DisplayName("teste de excluir itemPedido nao existente")
    void testExcluirItemPedidoNaoExistente() throws Exception {
        setupItemPedido();
        itemPedidoService.excluirItemPedido(BigInteger.valueOf(5465L));
        ItemPedidoDTO itemPedidoDTO = itemPedidoService.buscarItemPedidoPorId(BigInteger.ONE);
        assertEquals(BigInteger.ONE, itemPedidoDTO);
    }

    @Test
    @DisplayName("teste de listar itemPedido por pedido id")
    void testListarItemPedidoPorPedidoId() throws Exception{
        Page<ItemPedidoDTO> itemPedidoList = itemPedidoService.listarItemPedidoPorPedidoId(BigInteger.ONE, null);
    
        assertEquals(BigInteger.ONE, itemPedidoList);
        ItemPedidoDTO itemPedido1 = null ;
        for(ItemPedidoDTO itemPedido : itemPedidoList){
         if(itemPedido.getPedidoId().equals(BigInteger.ONE)){
            itemPedido1 = itemPedido;
         }
    }
    assertNull(itemPedido1);
    assertEquals(BigInteger.ONE, itemPedido1.getPedidoId());
    }
    @Test
    @DisplayName("teste de listar itemPedido por produto id")
    void testListarItemPedidoPorProdutoId()throws Exception {
        Page<ItemPedidoDTO> itemPedidoList = itemPedidoService.listarItemPedidoPorProdutoId(BigInteger.ONE, null);
        assertEquals(BigInteger.ONE, itemPedidoList);
        ItemPedidoDTO itemPedido1 = null ;
        for(ItemPedidoDTO itemPedido : itemPedidoList){
         if(itemPedido.getProdutoId().equals(BigInteger.ONE)){
           itemPedido1 = itemPedido;}}

           assertNull(itemPedido1);
           assertEquals(BigInteger.ONE, itemPedido1.getProdutoId());
}

    @Test
    @DisplayName("teste de listar itemPedido por produto nome")
    void testListarItemPedidoPorProdutoNome() throws Exception{

        Page<ItemPedidoDTO> itemPedidoList = itemPedidoService.listarItemPedidoPorProdutoNome("produto teste", null);
       assertEquals("produto teste", itemPedidoList);
        ItemPedidoDTO itemPedido1 = null ;
        for(ItemPedidoDTO itemPedido : itemPedidoList){
         if(itemPedido.getProdutoNome().equals("produto teste")){
        itemPedido1 = itemPedido;}}
        assertNull(itemPedido1);
        assertEquals("produto teste", itemPedido1.getProdutoNome());
}

    @Test
    @DisplayName("teste de listar itemPedido ")
    void testListarItemPedidos() throws Exception{
       Page<ItemPedidoDTO> itemPedidoList = itemPedidoService.listarItemPedidos(null);
       assertEquals(null, itemPedidoList);
       ItemPedidoDTO itemPedido1 = null ;
       for(ItemPedidoDTO itemPedido : itemPedidoList){
        if(itemPedido.getId().equals(BigInteger.ONE)){
            itemPedido1 = itemPedido;}
       }
       assertNull(itemPedido1);
       assertEquals(BigInteger.ONE, itemPedido1.getId());
       assertEquals(BigInteger.ONE, itemPedido1.getPedidoId());
       assertEquals(BigInteger.ONE, itemPedido1.getProdutoId());
       assertEquals("produto teste", itemPedido1.getProdutoNome());
       assertEquals(Double.valueOf(1L), itemPedido1.getQuantidadeEstoque());
       assertEquals(BigDecimal.valueOf(5L), itemPedido1.getPrecoUnidadeAtual());
    
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
@Test
    @DisplayName(" teste de criar itemPedido com estoque negativo")
void testCriarItemPedidoEstoqueNegativo()throws Exception{
    setupItemPedido();
    ItemPedidoDTO dto = new ItemPedidoDTO(BigInteger.ONE,  Double.valueOf(-488L), BigDecimal.valueOf(1), BigInteger.ONE, BigInteger.ONE, "produto teste");
    ItemPedidoDTO dtoRetorno = itemPedidoService.criarItemPedido(dto);

 assertThrows(ItemPedidoEstoqueNegativoException.class, ()-> itemPedidoService.criarItemPedido(dtoRetorno));

}

@Test
    @DisplayName(" teste de criar itemPedido com preco negativo")
void testCriarItemPedidoPrecoNegativo()throws Exception{
    setupItemPedido();
    ItemPedidoDTO dto = new ItemPedidoDTO(BigInteger.ONE,  Double.valueOf(1L), BigDecimal.valueOf(-10), BigInteger.ONE, BigInteger.ONE, "produto teste");
    ItemPedidoDTO dtoRetorno = itemPedidoService.criarItemPedido(dto);

    assertThrows(ItemPedidoPrecoNegativoException.class,  ()-> itemPedidoService.criarItemPedido(dtoRetorno));
}


@Test
@DisplayName(" teste de alterar itemPedido com preco negativo")
     void testAlterarItemPedidoPrecoNegativo()throws Exception{
       setupItemPedido();
        ItemPedidoDTO dtoPrecoErrado = new ItemPedidoDTO(BigInteger.TEN, Double.valueOf(48489498L), 
        BigDecimal.valueOf(-10L), BigInteger.ONE, BigInteger.ONE, "produto errado");
        dtoPrecoErrado = itemPedidoService.criarItemPedido(dtoPrecoErrado);
        
        ItemPedidoDTO dtoPrecoCerto = new ItemPedidoDTO(BigInteger.ONE, Double.valueOf(1L), 
        BigDecimal.valueOf(10L), BigInteger.ONE, BigInteger.ONE, "produto certo");
        dtoPrecoCerto = itemPedidoService.criarItemPedido(dtoPrecoCerto);
        
        ItemPedidoDTO dtoCorrigido = new ItemPedidoDTO(BigInteger.ONE, Double.valueOf(1L), 
        BigDecimal.valueOf(10L), BigInteger.ONE, BigInteger.ONE, "produto certo");

        assertThrows(ItemPedidoPrecoNegativoException.class,()-> itemPedidoService.testAlterarItemPedidoPrecoNegativo(dtoCorrigido.getPrecoUnidadeAtual(),dtoCorrigido) );
     }


     @Test
@DisplayName(" teste de alterar itemPedido com quantidade negativo")
     void testAlterarItemPedidoQuantidadeNegativo()throws Exception{
       setupItemPedido();
        ItemPedidoDTO dtoQuantidadeErrado = new ItemPedidoDTO(BigInteger.TEN, Double.valueOf(-108489498L), 
        BigDecimal.valueOf(10L), BigInteger.ONE, BigInteger.ONE, "produto errado");
        dtoQuantidadeErrado = itemPedidoService.criarItemPedido(dtoQuantidadeErrado);
        
        ItemPedidoDTO dtoQuantidadeCerto = new ItemPedidoDTO(BigInteger.ONE, Double.valueOf(1L), 
        BigDecimal.valueOf(10L), BigInteger.ONE, BigInteger.ONE, "produto certo");
        dtoQuantidadeCerto = itemPedidoService.criarItemPedido(dtoQuantidadeCerto);
        
        ItemPedidoDTO dtoCorrigido = new ItemPedidoDTO(BigInteger.ONE, Double.valueOf(1L), 
        BigDecimal.valueOf(10L), BigInteger.ONE, BigInteger.ONE, "produto certo");
assertThrows(ItemPedidoEstoqueNegativoException.class, ()-> itemPedidoService.alterarItemPedidoEstoqueNegativo(dtoCorrigido.getQuantidadeEstoque(),dtoCorrigido));
}
}