package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    Page<Cliente> findByNomeRazaoSocialContainingIgnoreCase(Pageable pageable, String nomeRazaoSocial);

    @Query("FROM Cliente e WHERE e.nomeRazaoSocial like %?1")
    List<Cliente> findByMinhaQuery(String nomeRazaoSocial);

    @Override
    Page<Cliente> findAll(Pageable pageable);

    boolean existsByNomeRazaoSocial (String nomeRazaoSocial);
    boolean existsByCpf(String cpf);
    boolean existsByCnpj(String cnpj);
    boolean existsByCnpjOrCpf(String cnpj, String cpf);
}
