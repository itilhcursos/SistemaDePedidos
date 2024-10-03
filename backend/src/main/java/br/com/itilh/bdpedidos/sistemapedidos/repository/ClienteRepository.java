package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;

@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, BigInteger> {

    boolean existsByCnpj(String cnpj);
    boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);

    Page<Cliente> findByNomeRazaoSocialContainingIgnoreCase(Pageable pageable, String txtBusca);
}
