package br.com.itilh.bdpedidos.sistemapedidos.controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
public class ProdutoController {

    private final ProdutoRepository repositorio;

    public ProdutoController(ProdutoRepository repositorio){
        this.repositorio = repositorio;
    }

    @GetMapping("/produtos")
    public Page<Produto> getTodos(
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);

        return  (Page<Produto>) repositorio.findAll(pageable);
    }

    @GetMapping("/produto/{id}")
    public Produto getProdutoPorId(@PathVariable BigInteger id) throws Exception {
            return repositorio.findById(id)
            .orElseThrow(() -> new Exception("ID não encontrado."));
    }
    
    @PostMapping("/produto")
    public Produto postProduto(@RequestBody Produto entity) throws Exception {
        try{ 
            return repositorio.save(entity);
        }catch (Exception ex){
            throw new Exception("Não foi possível criar um novo Produto" + ex.getMessage());
        }    
    }

    @PutMapping("/produto/{id}")
    public Produto putProduto(@PathVariable String id, @RequestBody Produto entity) throws Exception {
        try {
            return repositorio.save(entity);
        } catch (Exception ex) {
            throw new Exception("Não foi possível alterar o Produto." + ex.getMessage());
        }
    }
    
    @DeleteMapping("/produto/{id}")
    public String DeleteMethodName(@PathVariable BigInteger id) throws Exception {
        try {
            repositorio.deleteById(id);
            return "Excluído";
        } catch (Exception ex) {
            throw new Exception("Não foi possível deletar o ID informado." + ex.getMessage());
        }
    }

}
