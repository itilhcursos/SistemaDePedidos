package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, BigInteger> {
    // Select * from tb_estados where tx_nome = 'nome'//
    List<Produto> findByDescricao(String descricao);

    // Select * from tb_estados where UPPER(tx_nome) like UPPER('nome%')//
    List<Produto> findByDescricaoStartingWithIgnoreCase(String descricao);

    // Select * from tb_estados where UPPER(tx_nome) like UPPER('%nome')//
    List<Produto> findByDescricaoEndingWithIgnoreCase(String descricao);

    // Select * from tb_estados where UPPER(tx_nome) like UPPER('%nome%')//
    List<Produto> findByDescricaoContainingIgnoreCase(String descricao);

    List<Produto> findByQuantidadeEstoque(Double quantidadeEstoque);

    List<Produto> findByPrecoUnidadeAtual(BigDecimal precoUnidadeAtual);

    List<Produto> findByativo(Boolean ativo);

    @Query("FROM Produto e WHERE e.descricao like %?1")
    List<Produto> findByMinhaQuery(String nome);

    @Override
    Page<Produto> findAll(Pageable pageable);
}
