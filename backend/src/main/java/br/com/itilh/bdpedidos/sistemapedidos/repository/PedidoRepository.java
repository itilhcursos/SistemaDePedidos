package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.itilh.bdpedidos.sistemapedidos.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, BigInteger> {

}
