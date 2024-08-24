package br.com.itilh.bdpedidos.sistemapedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigInteger;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class EstadoDTO {

    private BigInteger id;
    private String nome;
}
