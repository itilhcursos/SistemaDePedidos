package br.com.itilh.bdpedidos.sistemapedidos.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

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

import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.util.ModoBusca;

@RestController
public class ProdutoController {

    private final ProdutoRepository repositorio;

    public ProdutoController(ProdutoRepository repositorio) {
        this.repositorio = repositorio;
    }

    @GetMapping("/produtos")
    public Page<Produto> getTodos(
            @RequestParam(required = false, defaultValue = "1") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "ASC") String direction,
            @RequestParam(required = false, defaultValue = "id") String property) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);

        return (Page<Produto>) repositorio.findAll(pageable);
    }

    @GetMapping("/produto/descricao/{descricao}")
    public List<Produto> getProdutoPorDescricao(@PathVariable String descricao,
            @RequestParam(required = true) ModoBusca modoBusca) {
        if (modoBusca.equals(ModoBusca.EXATO)) {
            return repositorio.findByDescricao(descricao);
        } else if (modoBusca.equals(ModoBusca.INICIADO)) {
            return repositorio.findByDescricaoStartingWithIgnoreCase(descricao);
        } else if (modoBusca.equals(ModoBusca.FINALIZADO)) {
            return repositorio.findByDescricaoEndingWithIgnoreCase(descricao);
        } else {
            return repositorio.findByDescricaoContainingIgnoreCase(descricao);
        }
    }

    @GetMapping("/produto/{id}")
    public Produto getPorId(@PathVariable BigInteger id) throws Exception {
        return repositorio.findById(id).orElseThrow(
                () -> new Exception("ID inválido."));
    }

    @PostMapping("/produto")
    public Produto criarProduto(@RequestBody Produto entity) throws Exception {
        try {
            if (entity.getId() != null) {
                throw new Exception("Entidade já existe.");
            }
            return repositorio.save(entity);
        } catch (Exception e) {
            throw new Exception("Erro ao salvar o Produto.");
        }
    }

    @PutMapping("/produto/{id}")
    public Produto alterarProduto(@PathVariable BigInteger id,
            @RequestBody Produto novosDados) throws Exception {

        Optional<Produto> produtoAmazenado = repositorio.findById(id);
        if (produtoAmazenado.isPresent()) {
            // Atribuir novo nome ao objeto já existem no banco de dados
            produtoAmazenado.get().setDescricao(novosDados.getDescricao());
            produtoAmazenado.get().setQuantidadeEstoque(novosDados.getQuantidadeEstoque());
            produtoAmazenado.get().setPrecoUnidadeAtual(novosDados.getPrecoUnidadeAtual());
            produtoAmazenado.get().setAtivo(novosDados.getAtivo());
            return repositorio.save(produtoAmazenado.get());
        }
        throw new Exception("Alteração não foi realizada.");
    }

    @DeleteMapping("/produto/{id}")
    public String deletePorId(@PathVariable BigInteger id) throws Exception {

        Optional<Produto> produtoAmazenado = repositorio.findById(id);
        if (produtoAmazenado.isPresent()) {
            repositorio.delete(produtoAmazenado.get());
            return "Excluído";
        }
        throw new Exception("Id não econtrado para a exclusão");
    }

}
