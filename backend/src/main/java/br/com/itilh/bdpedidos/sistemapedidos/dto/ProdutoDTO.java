package br.com.itilh.bdpedidos.sistemapedidos.dto;

import java.math.BigInteger;
import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {

    private BigInteger id;
    private BigInteger numero;
    private Date dataCompra;
    private Date dataEntrega;
    private Date dataPagamento;

    private BigInteger clInteid;
    private String clienteNome;
    private BigInteger formaPagamentoId;
    private String formaPagamentoNome;
}
