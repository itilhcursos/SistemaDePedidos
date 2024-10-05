package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, BigInteger> {

    List<Produto> findByDescricao(String descricao);
    List<Produto> findByDescricaoStartingWithIgnoreCase(String descricao);
    List<Produto> findByDescricaoEndingWithIgnoreCase(String descricao);
    List<Produto> findByDescricaoContainingIgnoreCase(String descricao);

    Page<Produto> findByDescricaoContainingIgnoreCase(Pageable pageable, String descricao);

    @Query("FROM FormaPagamento e WHERE e.descricao like %?1")
    List<Produto> findByMinhaQuery(String descricao);

    @Override
    Page<Produto> findAll(Pageable pageable);

    boolean existsByDescricao(String Descricao);
}
