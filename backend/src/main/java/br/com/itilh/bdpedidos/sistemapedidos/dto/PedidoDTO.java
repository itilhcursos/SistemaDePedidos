package br.com.itilh.bdpedidos.sistemapedidos.dto;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import br.com.itilh.bdpedidos.sistemapedidos.model.ItemPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private BigInteger id;
    private BigInteger numero;
    private Date dataCompra; 
    private Date dataEntrega; 
    private Date dataPagamento; 
    
    private BigInteger clienteId;
    private String clienteNomeRazaoSocial;

    private BigInteger formaPagamentoId;
    private String formaPagamentoDescricao; 

    private List<ItemPedido> itens;
}

