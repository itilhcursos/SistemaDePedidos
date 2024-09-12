package br.com.itilh.bdpedidos.sistemapedidos.dto;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private BigInteger id;
    private Integer numero;
    private Data compra;
    private Data entrega;
    private Data pagamento;

    private BigInteger clienteId;
    private String clienteNome;

    private BigInteger formaPagamentoId;
    private String formaPagamentoNome;
}
