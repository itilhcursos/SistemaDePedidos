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

    List<Produto> findByNome(String nome);

    List<Produto> findByNomeStartingWithIgnoreCase(String nome);

    List<Produto> findByNomeEndingWithIgnoreCase(String nome);

    List<Produto> findByNomeContainingIgnoreCase(String nome);
  
    @Query("FROM Produto e WHERE e.nome like %?1")
    List<Produto> findByMinhaQuery(String nome);
    
    @Override
    Page<Produto> findAll(Pageable pageable);
}
