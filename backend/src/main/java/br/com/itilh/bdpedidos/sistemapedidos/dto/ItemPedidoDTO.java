package br.com.itilh.bdpedidos.sistemapedidos.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ItemPedidoDTO {
     
    private BigInteger id;
    private BigInteger pedidoId; 

   
    private BigInteger produtoId;
    private String produtoDescricao;
    private String produtoUrlImagem;


    private Double quantidadeEstoque;
    private BigDecimal precoUnidadeAtual;
}
