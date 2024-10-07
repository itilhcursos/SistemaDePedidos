package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, BigInteger> {

    List<FormaPagamento> findByDescricao(String descricao);

    List<FormaPagamento> findByDescricaoStartingWithIgnoreCase(String descricao);

    boolean existsByDescricao(String descricao);
    
    Page<FormaPagamento> findByDescricaoContainingIgnoreCase(Pageable pageable, String descricao);

}
