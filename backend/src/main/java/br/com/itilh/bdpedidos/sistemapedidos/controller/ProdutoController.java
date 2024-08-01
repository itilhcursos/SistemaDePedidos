package br.com.itilh.bdpedidos.sistemapedidos.controller;

import java.math.BigInteger;
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

@RestController
public class ProdutoController {
    private final ProdutoRepository repositorio;
    public ProdutoController(ProdutoRepository repositorio){
        this.repositorio = repositorio;
    }

    @GetMapping("/produtos")
    public Page<Produto> getTodosProdutos(
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.Direction.valueOf(direction), property);
        return (Page<Produto>) repositorio.findAll(pageable);
    }

    @GetMapping("/produto/{id}")
    public Produto getProdutoPorId(@PathVariable BigInteger id) throws Exception {
        return repositorio.findById(id).orElseThrow(() -> new Exception("ID Não Encontrado!"));
    }
    
    // "Post não funcionando por algum motivo maluco. Quando passando ID "null", o valor "1" sempre é passado"
    // 01/08/24: Resolvido. Problema estava no Sequence do Produto dentro do Banco de Dados. O valor não estava refletindo a quantidade de itens presentes na tabela. Alterar o "Current value" pra 99 consertou o problema.
    // Explicação: Aconteceu que o Sequence tava "zerado" (no meu caso tava com valor 4). Então ele estava incrementando o valor ID corretamente, porém, como ja existiam itens com os IDs que ele gerava, dava erro.

    @PostMapping("/produto")
    public Produto criarProduto(@RequestBody Produto entity) throws Exception {
        try{               
            if(entity.getId() != null){
                throw new Exception("Entidade já existe.");
            }
            return repositorio.save(entity);
        }catch(Exception e){
            throw new Exception("Erro ao salvar o produto.");
        }
    }
    
    @PutMapping("/produto/{id}")
    public Produto alterarProduto(@PathVariable BigInteger id, @RequestBody Produto novosDados) throws Exception {
        Optional<Produto> produtoArmazenado = repositorio.findById(id);
        if (produtoArmazenado.isPresent()){
            produtoArmazenado.get().setDescricao(novosDados.getDescricao());
            produtoArmazenado.get().setQuantidadeEstoque(novosDados.getQuantidadeEstoque());
            produtoArmazenado.get().setPrecoUnidadeAtual(novosDados.getPrecoUnidadeAtual());
            produtoArmazenado.get().setAtivo(novosDados.getAtivo());
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
