package br.com.itilh.bdpedidos.sistemapedidos.repository;

import java.math.BigInteger;
import br.com.itilh.bdpedidos.sistemapedidos.model.Pedido1;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PedidoRepository extends JpaRepository<Pedido1, BigInteger> {
    

}