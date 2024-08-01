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

    List<FormaPagamento> findByDescricao(String descricao);

    List<FormaPagamento> findByDescricaoStartingWithIgnoreCase(String descricao);

    List<FormaPagamento> findByDescricaoEndingWithIgnoreCase(String descricao);

    List<FormaPagamento> findByDescricaoContainingIgnoreCase(String descricao);

    @Query("FROM FormaPagamento e WHERE e.descricao like %?1")
    List<FormaPagamento> findByMinhaQuery(String descricao);
    
    @Override
    Page<FormaPagamento> findAll(Pageable pageable);

}
