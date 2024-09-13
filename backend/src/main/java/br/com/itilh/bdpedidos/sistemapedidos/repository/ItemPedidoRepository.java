package br.com.itilh.bdpedidos.sistemapedidos.repository;
import br.com.itilh.bdpedidos.sistemapedidos.model.ItemPedido;
import java.math.BigInteger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository  extends JpaRepository<ItemPedido, BigInteger>{

    Page<ItemPedido> findByprodutoId(BigInteger id,Pageable pageable);
    Page<ItemPedido> findBypedidoId(BigInteger id,Pageable pageable);
    Page<ItemPedido> findByprodutoNomeIgnoreCase(String nome,Pageable pageable);




    @SuppressWarnings("null")
    @Override
    Page<ItemPedido> findAll(Pageable pageable);

}
