package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.itilh.bdpedidos.sistemapedidos.model.Estado;

@Repository
public interface EstadoRepository extends CrudRepository<Estado, BigInteger> {
    // Select * from tb_estados where tx_nome = 'nome'//
    List<Estado> findByNome(String nome);

    // Select * from tb_estados where UPPER(tx_nome) like UPPER('nome%')//
    List<Estado> findByNomeStartingWithIgnoreCase(String nome);

    // Select * from tb_estados where UPPER(tx_nome) like UPPER('%nome')//
    List<Estado> findByNomeEndingWithIgnoreCase(String nome);

    // Select * from tb_estados where UPPER(tx_nome) like UPPER('%nome%')//
    List<Estado> findByNomeContainingIgnoreCase(String nome);

    @Query("FROM Estado e WHERE e.nome like %?1")
    List<Estado> findByMinhaQuery(String nome);
}
