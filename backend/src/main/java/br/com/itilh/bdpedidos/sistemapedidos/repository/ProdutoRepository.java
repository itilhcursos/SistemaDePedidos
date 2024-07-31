package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, BigInteger> {

    List<Produto> findByDescricao(String descricao);

    List<Produto> findByDescricaoStartingWithIgnoreCase(String descricao);

    List<Produto> findByDescricaoEndingWithIgnoreCase(String descricao);

    List<Produto> findByDescricaoContainingIgnoreCase(String descricao);

    

}
