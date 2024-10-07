package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.model.Municipio;


@Repository
public interface MunicipioRepository extends JpaRepository<Municipio , BigInteger > {

    Page<Municipio> findByEstadoId(BigInteger id, Pageable pageable);
    Page<Municipio> findByEstadoNomeIgnoreCase(String nome, Pageable pageable);

    List<Municipio> findByNomeContainingIgnoreCase(String nome);

    Page<Municipio> findByNomeContainingIgnoreCase(Pageable pageable, String nome);


    boolean existsByNomeAndEstadoId(String nome, BigInteger id);

}
