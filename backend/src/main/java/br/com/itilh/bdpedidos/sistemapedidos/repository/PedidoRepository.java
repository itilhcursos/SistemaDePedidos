package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import br.com.itilh.bdpedidos.sistemapedidos.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, BigInteger>{

    Page<Pedido> findByclienteId(BigInteger id,Pageable pageable);
    Page<Pedido> findByformaPagamentoId(BigInteger id,Pageable pageable);

    Page<Pedido> findByclienteNomeIgnoreCase(String nome,Pageable pageable);
    Page<Pedido> findByformaPagamentoNomeIgnoreCase(String nome,Pageable pageable);

    boolean existsByNumero (Integer numero);

    @SuppressWarnings("null")
    @Override
    Page<Pedido> findAll(Pageable pageable);

}


