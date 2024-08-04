package br.com.itilh.bdpedidos.sistemapedidos.controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class ProdutoController {

    private final ProdutoRepository repository;

    // Construtor para settar o repository 
    public ProdutoController (ProdutoRepository repository){
        this.repository = repository;
    }

    // Endpoint para obter todos os registros da tabela
    // @GetMapping("/produtos")
    // public List<Produto> getAll () {
    //     return (List<Produto>) repository.findAll();
    // }
    @GetMapping("/produtos")
    public Page<Produto> getTodos(
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);

        return  (Page<Produto>) repository.findAll(pageable);
    }

    @GetMapping("/produto/{id}")
    public Produto getById (@PathVariable BigInteger id) throws Exception {
        return repository.findById(id).orElseThrow(
            () -> new Exception("Id inválido")
        );
    }

    @PostMapping("/produto")
    public Produto posProduto (@RequestBody Produto entity) throws Exception {
        try {
            return repository.save(entity);
        } catch (Exception e) {
            throw new Exception("Erro ao salvar o produto");
        }
    }

    @PutMapping("produto/{id}")
    public Produto editProduto (@PathVariable BigInteger id, @RequestBody Produto entity) throws Exception {
        Optional<Produto> atualData = repository.findById(id);
        if (atualData.isPresent()) {
            atualData.get().setDescricao(entity.getDescricao());
            atualData.get().setQuantidadeEstoque(entity.getQuantidadeEstoque());
            atualData.get().setPrecoUnidadeAtual(entity.getPrecoUnidadeAtual());
            atualData.get().setAtivo(entity.getAtivo());
            return repository.save(atualData.get());
        }
        throw new Exception("Não foi possível alterar o produto informado");
    }

    @DeleteMapping("produto/{id}")
    public String deleteById (@PathVariable BigInteger id) throws Exception {
        try {
            repository.deleteById(id);
            return "Excluído";
        } catch (Exception ex) {
            throw new Exception("Não foi possível excluir o produto informado" + ex.getMessage());
        }
    }
}
