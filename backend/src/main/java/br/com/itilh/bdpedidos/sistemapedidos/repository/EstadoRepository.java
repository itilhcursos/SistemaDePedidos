package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.itilh.bdpedidos.sistemapedidos.model.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, BigInteger> {

    List<Estado> findByNome(String nome);

    List<Estado> findByNomeStartingWithIgnoreCase(String nome);

    List<Estado> findByNomeEndingWithIgnoreCase(String nome);

    List<Estado> findByNomeContainingIgnoreCase(String nome);

    @Query("FROM Estado e WHERE e.nome like %?1")
    List<Estado> findByMinhaQuery(String nome);

    @Override
    Page<Estado> findAll(Pageable pageable);

    boolean existsByNome(String nome);
}
