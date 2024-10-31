package br.com.itilh.bdpedidos.sistemapedidos.repository;

import br.com.itilh.bdpedidos.sistemapedidos.model.ItemPedido;


import java.math.BigDecimal;
import java.math.BigInteger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemPedidoRepository  extends JpaRepository<ItemPedido, BigInteger>{
    Page<ItemPedido> findByPedidoId(BigInteger pedidoId,Pageable pageable);
    Page<ItemPedido> findByProdutoId(BigInteger produtoId,Pageable pageable);
    Page<ItemPedido> findByProdutoDescricaoIgnoreCase(String descricao, Pageable pageable);

    boolean existsByQuantidadeEstoque(Double quantidadeEstoque );
    boolean existsByPrecoUnidadeAtual(BigDecimal precoUnidadeAtual);
    boolean existsByPedidoId( BigInteger pedidoId);
    boolean existsByProdutoId( BigInteger produtoId);

    @SuppressWarnings("null")
    @Override
    Page<ItemPedido> findAll(Pageable pageable);

    

}
