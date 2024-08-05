package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.itilh.bdpedidos.sistemapedidos.model.Municipio;


@Repository
public interface MunicipioRepository extends JpaRepository<Municipio , BigInteger > {

    List<Municipio> findByEstadoId(BigInteger id);
    List<Municipio> findByEstadoNomeIgnoreCase(String nome);

}
