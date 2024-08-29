package br.com.itilh.bdpedidos.sistemapedidos.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ProdutoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;

@SpringBootTest
@ActiveProfiles("test") 
public class ProdutoServiceTest {

    @Autowired
    ProdutoService service;

     void setUpProduto() {
        Produto produto = new Produto(BigInteger.ONE, "protudo teste", null, null, null);
   
    }
    //============================================================
    @Test
    void testAlterarProduto()throws Exception {

    }
    //========================================================
    @Test
    void testBuscarProdutoPorId() throws Exception {

    }
    //========================================================
    @Test
    @DisplayName("Test de criação de um produto")
    void testCriarProduto() throws Exception {
       ProdutoDTO testeDTO = new ProdutoDTO(null, "produto de teste", null, null, true);
       ProdutoDTO retorna = service.criarProduto(testeDTO);
       assertEquals(true, retorna.getId() != null);


    }
    //==========================================================
    @Test
    void testExcluirProduto() throws Exception {

    }
    //==========================================================
    @Test
    void testListarProdutos() throws Exception {

    }


}
