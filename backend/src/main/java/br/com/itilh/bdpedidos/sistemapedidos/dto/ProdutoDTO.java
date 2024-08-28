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
public class ProdutoDTO {

    private BigInteger id;
    private String descricao;
    private Double quantidadeEstoque;
    private BigDecimal precoUnidadeAtual;
    private Boolean ativo;
    
}
