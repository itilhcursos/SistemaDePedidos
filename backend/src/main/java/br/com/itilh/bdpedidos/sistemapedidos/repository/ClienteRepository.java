package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;



@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, BigInteger> {

    List<Cliente> findBynomeRazaoSocial(String nomeRazaoSocial);
    List<Cliente> findBynomeRazaoSocialStartingWithIgnoreCase(String nomeRazaoSocial);
    List<Cliente> findBynomeRazaoSocialEndingWithIgnoreCase(String nomeRazaoSocial);
    List<Cliente> findBynomeRazaoSocialContainingIgnoreCase(String nomeRazaoSocial);

     Page<Cliente> findBymunicipioId(BigInteger id, Pageable pageable);
    Page<Cliente> findBymunicipioNomeIgnoreCase(String nome, Pageable pageable);

     
    @SuppressWarnings("null")
    @Override
    Page<Cliente> findAll(Pageable pageable);

}
