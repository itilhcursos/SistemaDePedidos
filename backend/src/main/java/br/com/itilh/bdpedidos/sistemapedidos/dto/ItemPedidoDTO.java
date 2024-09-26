package br.com.itilh.bdpedidos.sistemapedidos.dto;
import java.math.BigDecimal;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoDTO {

    private BigInteger id;
    private Double quantidadeEstoque;
    private BigDecimal precoUnidadeAtual;

    private BigInteger pedidoId;

    private BigInteger produtoId;
    private String produtoDescricao;

}
