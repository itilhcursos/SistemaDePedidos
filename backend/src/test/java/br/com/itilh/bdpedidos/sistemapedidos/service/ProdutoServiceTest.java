package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ProdutoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoQuantidadeEstoqueException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoPrecoUnidadeAtualException;


@SpringBootTest
@ActiveProfiles("teste")
public class ProdutoServiceTest {
    
    @Autowired
    private ProdutoService produtoService;

    @Test
    @DisplayName("Teste de negocio - Produto com estoque negativo")
    void testCriarProdutoComEstoqueNegativo() throws Exception {
        ProdutoDTO produtoDTO = new ProdutoDTO()null, "Produto Teste", -2.0,
    }

}
//incompleto