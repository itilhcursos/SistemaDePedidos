package br.com.itilh.bdpedidos.sistemapedidos.dto;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PedidoDTO {

    private BigInteger id;
    private BigInteger numero;
    private BigInteger clienteId;
    private String clienteNome;
    private BigInteger formaPagamentoId;
    private String formaPagamentoDescricao;
    private LocalDate dataCompra;
    private LocalDate dataEntrega;
    private LocalDate dataPagamento;
    private List<ItemPedidoDTO> itens;
}