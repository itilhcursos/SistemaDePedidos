package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ItemPedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, BigInteger> {
    @SuppressWarnings("null")
    @Override
    Page<Pedido> findAll(Pageable pageable);

    Page<Pedido> findByclienteId(BigInteger id, Pageable pageable);

    Page<Pedido> findByformaPagamentoId(BigInteger id, Pageable pageable);

    Page<Pedido> findByitens(List<ItemPedidoDTO> itens, Pageable pageable);

    Page<Pedido> findByclienteNomeIgnoreCase(String nome, Pageable pageable);

    Page<Pedido> findByformaPagamentoNomeIgnoreCase(String nome, Pageable pageable);

}
