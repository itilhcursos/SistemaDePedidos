package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;

@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, BigInteger> {

    Page<Cliente> findByMunicipioNomeIgnoreCase(String nome, Pageable pageable);

    boolean existsByNomeRazaoSocialAndMunicipioId(String nomeRazaoSocial, BigInteger id);
}
