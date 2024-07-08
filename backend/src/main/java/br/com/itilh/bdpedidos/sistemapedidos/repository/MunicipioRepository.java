package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.itilh.bdpedidos.sistemapedidos.model.Municipio;
import java.util.List;


@Repository
public interface MunicipioRepository extends CrudRepository<Municipio , BigInteger > {

    List<Municipio> findByEstadoId(BigInteger id);
    List<Municipio> findByEstadoNomeIgnoreCase(String nome);

}
