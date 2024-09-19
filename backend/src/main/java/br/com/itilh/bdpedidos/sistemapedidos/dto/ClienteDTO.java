package br.com.itilh.bdpedidos.sistemapedidos.dto;

import lombok.NoArgsConstructor;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

    private BigInteger id;
    private String nomeRazaoSocial;
    private String cnpj;
    private String telefone;
    private String endereco;
    private String bairro;
    private String cep;
    private String email;
    private Boolean ativo;
    private String informacoes;
    private String cpf;

    private BigInteger municipioId;
    private String municipioNome;

}
