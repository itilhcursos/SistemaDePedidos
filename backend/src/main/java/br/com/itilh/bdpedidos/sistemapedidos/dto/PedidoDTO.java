package br.com.itilh.bdpedidos.sistemapedidos.dto;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private BigInteger id;
    private BigInteger numero;
    private LocalDate dataCompra; 
    private LocalDate dataEntrega; 
    private LocalDate dataPagamento; 
    
    private BigInteger clienteId;
    private String clienteNomeRazaoSocial;

    private BigInteger formaPagamentoId;
    private String formaPagamentoDescricao; 

    private List<ItemPedidoDTO> itens;
}

