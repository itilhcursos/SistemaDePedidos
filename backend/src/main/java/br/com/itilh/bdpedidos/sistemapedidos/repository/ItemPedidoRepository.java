package br.com.itilh.bdpedidos.sistemapedidos.repository;

import br.com.itilh.bdpedidos.sistemapedidos.model.ItemPedido;
import br.com.itilh.bdpedidos.sistemapedidos.model.Pedido;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;

import java.math.BigDecimal;
import java.math.BigInteger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemPedidoRepository  extends JpaRepository<ItemPedido, BigInteger>{
    Page<ItemPedido>findByprodutoNomeIgnoreCase(String nome, Pageable pageable);
    Page<ItemPedido> findBypedidoId(Pedido pedidoId,Pageable pageable);
    Page<ItemPedido> findByprodutoId(Produto produtoId,Pageable pageable);
    boolean existsByQuantidadeEstoque(Double quantidadeEstoque );

     boolean existsByPrecoUnidadeAtual(BigDecimal precoUnidadeAtual);

    boolean existsByPedidoId( BigInteger id);


    @SuppressWarnings("null")
    @Override
    Page<ItemPedido> findAll(Pageable pageable);

    

}
