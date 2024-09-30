package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.itilh.bdpedidos.sistemapedidos.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, BigInteger>{

}
