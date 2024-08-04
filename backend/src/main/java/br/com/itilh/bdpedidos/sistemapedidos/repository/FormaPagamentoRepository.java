package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, BigInteger> {

    // Select * from tb_formaPagamento where tx_nome = 'nome'//
    List<FormaPagamento> findByAtivo(Boolean ativo);

    // Select * from tb_formaPagamento where tx_nome = 'nome'//
    List<FormaPagamento> findByDescricao(String descricao);

    // Select * from tb_formaPagamento where UPPER(tx_nome) like UPPER('nome%')//
    List<FormaPagamento> findByDescricaoStartingWithIgnoreCase(String descricao);

    // Select * from tb_formaPagamento where UPPER(tx_nome) like UPPER('%nome')//
    List<FormaPagamento> findByDescricaoEndingWithIgnoreCase(String descricao);

    // Select * from tb_formaPagamento where UPPER(tx_nome) like UPPER('%nome%')//
    List<FormaPagamento> findByDescricaoContainingIgnoreCase(String descricao);

    @Query("FROM FormaPagamento e WHERE e.descricao like %?1")
    List<FormaPagamento> findByMinhaQuery(String nome);

    @Override
    Page<FormaPagamento> findAll(Pageable pageable);
}
