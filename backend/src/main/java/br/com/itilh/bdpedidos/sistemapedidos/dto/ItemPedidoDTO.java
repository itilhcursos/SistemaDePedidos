package br.com.itilh.bdpedidos.sistemapedidos.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

<<<<<<< HEAD
=======

>>>>>>> developer
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

<<<<<<< HEAD

=======
>>>>>>> developer
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ItemPedidoDTO {

    private BigInteger id;
    private BigInteger pedidoId;
<<<<<<< HEAD
    private BigInteger produtoId;
    private String produtoDescricao;
    private String produtoUrlImagem;
    private Double quantidadeEstoque;
    private BigDecimal precoUnidadeAtual;
    
=======

    private BigInteger produtoId;
    private String produtoDescricao;
    private String produtoUrlImagem;

    private Double quantidadeEstoque;
    private BigDecimal precoUnidadeAtual;
>>>>>>> developer

}
