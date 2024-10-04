package br.com.itilh.bdpedidos.sistemapedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.itilh.bdpedidos.sistemapedidos.model.ItemPedido;

public interface ItemPedidoRepository<BigInteger> extends JpaRepository<ItemPedido, BigInteger> {

}
