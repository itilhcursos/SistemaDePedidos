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
    // Select * from tb_produtos where tx_descricao = 'nome'//
    List<Produto> findByDescricao(String descricao);

    // Select * from tb_produtos where UPPER(tx_descricao) like UPPER('nome%')//
    List<Produto> findByDescricaoStartingWithIgnoreCase(String descricao);

    // Select * from tb_produtos where UPPER(tx_descricao) like UPPER('%nome')//
    List<Produto> findByDescricaoEndingWithIgnoreCase(String descricao);

    // Select * from tb_produtos where UPPER(tx_descricao) like UPPER('%nome%')//
    List<Produto> findByDescricaoContainingIgnoreCase(String descricao);

    @Query("FROM Produto e WHERE e.descricao like %?1")
    List<Produto> findByMinhaQuery(String descricao);
    
    @SuppressWarnings("null")
    @Override
    Page<Produto> findAll(Pageable pageable);
}
