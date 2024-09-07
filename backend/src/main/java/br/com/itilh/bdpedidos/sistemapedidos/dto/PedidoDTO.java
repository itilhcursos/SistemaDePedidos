package br.com.itilh.bdpedidos.sistemapedidos.dto;

import java.math.BigInteger;
import java.util.Date;

import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
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
public class PedidoDTO {
    
    private BigInteger id;
    private Cliente cliente;
    private FormaPagamento formaPagamento;
    private BigInteger intNumero;
    private Date dtCompra;
    private Date dtEntrega;
    private Date dtPagamento;
}
