package br.com.itilh.bdpedidos.sistemapedidos.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ProdutoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.service.ProdutoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class ProdutoController {

    @Autowired
    ProdutoService produtoService;

    @GetMapping("/produtos")
    public Page<ProdutoDTO> getTodosProdutos(
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.Direction.valueOf(direction), property);
        return produtoService.listarProdutos(pageable);
    }

    @GetMapping("/produtos/{txtBusca}")
    public Page<ProdutoDTO> getBusca(
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property,
        @PathVariable String txtBusca
    ) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.Direction.valueOf(direction), property);
        return produtoService.buscar(pageable, txtBusca);
    }

    @GetMapping("/produto/{id}")
    public ProdutoDTO getProdutoPorId(@PathVariable BigInteger id) throws Exception {
        return produtoService.buscarProdutoPorId(id);
    }    

    @PostMapping("/produto")
    public ProdutoDTO criarProduto(@RequestBody ProdutoDTO entity) throws Exception {
        return produtoService.criarProduto(entity);
    }
    
    @PutMapping("/produto/{id}")
    public ProdutoDTO alterarProduto(@PathVariable BigInteger id, @RequestBody ProdutoDTO novosDados) throws Exception {
        return produtoService.alterarProduto(id, novosDados);
    }

    @DeleteMapping("/produto/{id}")
    public String deleteProduto(@PathVariable BigInteger id) throws Exception {
        return produtoService.excluirProduto(id);
    }
}

