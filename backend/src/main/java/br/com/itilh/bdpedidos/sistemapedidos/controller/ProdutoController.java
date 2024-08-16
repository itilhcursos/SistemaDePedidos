package br.com.itilh.bdpedidos.sistemapedidos.controller;

<<<<<<< HEAD
import org.springframework.web.bind.annotation.RestController;

import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.util.ModoBusca;

import java.math.BigInteger;
import java.util.List;
=======
import java.math.BigInteger;
>>>>>>> develop
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class ProdutoController {

    private final ProdutoRepository repositorio;

=======
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;

@RestController
public class ProdutoController {
    private final ProdutoRepository repositorio;
>>>>>>> develop
    public ProdutoController(ProdutoRepository repositorio){
        this.repositorio = repositorio;
    }

    @GetMapping("/produtos")
<<<<<<< HEAD
    public Page<Produto> getTodos(
=======
    public Page<Produto> getTodosProdutos(
>>>>>>> develop
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ) {
<<<<<<< HEAD
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);

        return  (Page<Produto>) repositorio.findAll(pageable);
    }

    @GetMapping("/produtos/descricao/{descricao}")
    public List<Produto> getProdutosPorDescricao(@PathVariable String descricao, @RequestParam(required = true) ModoBusca modoBusca) {
        if(modoBusca.equals(ModoBusca.EXATO)){
            return repositorio.findByDescricao(descricao);
        }else if (modoBusca.equals(ModoBusca.INICIADO)){
            return repositorio.findByDescricaoStartingWithIgnoreCase(descricao);
        }else if (modoBusca.equals(ModoBusca.FINALIZADO)){
            return repositorio.findByDescricaoEndingWithIgnoreCase(descricao);
        }else{
            return repositorio.findByDescricaoContainingIgnoreCase(descricao);
        }
    }

    @GetMapping("/produto/{id}")
    public Produto getPorId(@PathVariable BigInteger id) throws Exception {
        return repositorio.findById(id).orElseThrow(
            () -> new Exception("ID inválido.")
        );
    }

    @PostMapping("/produto")
    public Produto criaProduto(@RequestBody Produto entity) throws Exception 
    {
=======
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.Direction.valueOf(direction), property);
        return (Page<Produto>) repositorio.findAll(pageable);
    }

    @GetMapping("/produto/{id}")
    public Produto getProdutoPorId(@PathVariable BigInteger id) throws Exception {
        return repositorio.findById(id).orElseThrow(() -> new Exception("ID Não Encontrado!"));
    }
    

    @PostMapping("/produto")
    public Produto criarProduto(@RequestBody Produto entity) throws Exception {
>>>>>>> develop
        try{               
            if(entity.getId() != null){
                throw new Exception("Entidade já existe.");
            }
            return repositorio.save(entity);
        }catch(Exception e){
            throw new Exception("Erro ao salvar o produto.");
        }
    }
<<<<<<< HEAD

    @PutMapping("/produto/{id}")
    public Produto alterarProduto(@PathVariable BigInteger id, @RequestBody Produto novosDados) throws Exception {

        Optional<Produto> produtoArmazenado = repositorio.findById(id);
        if (produtoArmazenado.isPresent()) {
            //Atribuir novo nome ao objeto já existente no banco de dados
=======
    
    @PutMapping("/produto/{id}")
    public Produto alterarProduto(@PathVariable BigInteger id, @RequestBody Produto novosDados) throws Exception {
        Optional<Produto> produtoArmazenado = repositorio.findById(id);
        if (produtoArmazenado.isPresent()){
>>>>>>> develop
            produtoArmazenado.get().setDescricao(novosDados.getDescricao());
            produtoArmazenado.get().setQuantidadeEstoque(novosDados.getQuantidadeEstoque());
            produtoArmazenado.get().setPrecoUnidadeAtual(novosDados.getPrecoUnidadeAtual());
            produtoArmazenado.get().setAtivo(novosDados.getAtivo());
<<<<<<< HEAD

            return repositorio.save(produtoArmazenado.get());
        }
        throw new Exception("Alteração não foi realizada.");
    }

    @DeleteMapping("/produto/{id}")
    public String deletePorId(@PathVariable BigInteger id) throws Exception {
        Optional<Produto> produtoArmazenado = repositorio.findById(id);
        if (produtoArmazenado.isPresent()) {
            repositorio.delete(produtoArmazenado.get());
            return "Excluído";
        }
        throw new Exception("Id não econtrado para a exclusão");
    }

}
=======
            return repositorio.save(produtoArmazenado.get());
        }
        throw new Exception ("Não foi possível alterar o Produto.");
    }

    @DeleteMapping("/produto/{id}")
    public String deleteProduto(@PathVariable BigInteger id) throws Exception {
        try{
            repositorio.deleteById(id);
            return "Excluído!";
        } catch (Exception e) {
            throw new Exception("Não foi possível apagar o ID fornecido!" + e.getMessage());
        }
    }
}

>>>>>>> develop
