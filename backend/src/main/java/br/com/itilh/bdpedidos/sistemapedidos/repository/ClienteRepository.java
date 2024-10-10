package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;

@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, BigInteger> {

    List<Cliente> findByNomeRazaoSocial(String nomeRazaoSocial);

    List<Cliente> findByNomeRazaoSocialStartingWithIgnoreCase(String nomeRazaoSocial);

    List<Cliente> findByNomeRazaoSocialEndingWithIgnoreCase(String nomeRazaoSocial);

    List<Cliente> findByNomeRazaoSocialContainingIgnoreCase(String nomeRazaoSocial);

    @Query("FROM Cliente e WHERE e.nomeRazaoSocial like %?1")
    List<Cliente> findByMinhaQuery(String nomeRazaoSocial);

    boolean existsByNomeRazaoSocial (String nomeRazaoSocial);
}