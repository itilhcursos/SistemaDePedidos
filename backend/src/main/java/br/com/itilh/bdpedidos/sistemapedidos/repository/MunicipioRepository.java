package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.itilh.bdpedidos.sistemapedidos.model.Municipio;


@Repository
public interface MunicipioRepository extends JpaRepository<Municipio , BigInteger > {

    Page<Municipio> findByEstadoId(BigInteger id, Pageable pageable);
    Page<Municipio> findByEstadoNomeIgnoreCase(String nome, Pageable pageable);

    boolean existsByNomeAndEstadoId(String nome, BigInteger id);

}
