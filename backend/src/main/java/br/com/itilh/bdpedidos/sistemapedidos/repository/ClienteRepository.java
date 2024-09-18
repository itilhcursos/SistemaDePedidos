package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, BigInteger> {

    Page<Cliente> findByMunicipioId(BigInteger id, Pageable pageable);
    Page<Cliente> findByMunicipioNomeIgnoreCase(String nome, Pageable pageable);

    boolean existsByCnpjAndMunicipioId(String cnpj, BigInteger id);
    boolean existsByCpfAndMunicipioId(String cpf, BigInteger id);

}
