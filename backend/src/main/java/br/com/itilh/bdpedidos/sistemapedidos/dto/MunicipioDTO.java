package br.com.itilh.bdpedidos.sistemapedidos.dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MunicipioDTO {

    private BigInteger id;
    private String nome;
    private Boolean entrega;

    private BigInteger estadoId;
    private String estadoNome;

}
