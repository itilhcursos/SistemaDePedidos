package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ItemPedidoDTO;

import br.com.itilh.bdpedidos.sistemapedidos.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, BigInteger> {

    Page<Pedido> findByclienteId(BigInteger id, Pageable pageable);

    Page<Pedido> findByformaPagamentoId(BigInteger id, Pageable pageable);

    Page<Pedido> findByclienteNomeRazaoSocialIgnoreCase(String nomeRazaoSocial, Pageable pageable);

    Page<Pedido> findByformaPagamentoDescricaoIgnoreCase(String descricao, Pageable pageable);

    boolean existsByNumero(Integer numero);

    @Query("SELECT p FROM Pedido p JOIN p.itens i WHERE i IN :itens")
    Page<Pedido> findByItens(@Param("itens") List<ItemPedidoDTO> itens, Pageable pageable);

    @SuppressWarnings("null")
    @Override
    Page<Pedido> findAll(Pageable pageable);
}
