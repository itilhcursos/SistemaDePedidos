package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;

@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, BigInteger> {

    Page<Cliente> findByNomeRazaoSocialContainingIgnoreCase(Pageable pageable, String nomeRazaoSocial);

    boolean existsByCpf(String cpf);
    
    boolean existsByCnpj(String cnpj);
}
