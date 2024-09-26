package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ProdutoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoDuplicadoException;

@SpringBootTest
@ActiveProfiles("test")
public class ProdutoServiceTest {

    @Autowired
    ProdutoService produtoService;

    @Test
    void testCriarProduto() throws Exception{
        ProdutoDTO dto = new ProdutoDTO(null, "Produto test",Double.valueOf(15),BigDecimal.TEN, true, "https://th.bing.com/th?id=ORMS.b8242f77aedd1ca11f209edc03a4bd57&pid=Wdp&w=612&h=304&qlt=90&c=1&rs=1&dpr=1&p=0");
        ProdutoDTO dtoRetorno = produtoService.criarProduto(dto);

        assertEquals(true, dtoRetorno.getId() != null);
    }
    @Test
    @DisplayName("Teste de regra de negócio -  Produto Duplicado")
    void testCriarProdutoDuplicado() throws Exception {
   
     ProdutoDTO dto = new ProdutoDTO(null,"Produto teste",Double.valueOf(20), BigDecimal.TEN, true, "String");
        //  ProdutoDTO Retorno = produtoService.criarProduto(dto);
        
        assertThrows(ProdutoDuplicadoException.class, ()-> produtoService.criarProduto(dto));
    }
}
