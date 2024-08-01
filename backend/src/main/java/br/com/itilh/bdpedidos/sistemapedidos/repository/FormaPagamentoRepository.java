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

    List<FormaPagamento> findByNome(String nome);

    List<FormaPagamento> findByNomeStartingWithIgnoreCase(String nome);


    List<FormaPagamento> findByNomeEndinWithIgnoreCase(String nome);
    
    List<FormaPagamento> findByNomeContainingIgnoreCase(String nome);

     @Query("FROM FormaPagamento e WHERE e.nome like %?1")
    List<FormaPagamento> findByMinhaQuery(String nome);
    
    @Override
    Page<FormaPagamento> findAll(Pageable pageable);



}
