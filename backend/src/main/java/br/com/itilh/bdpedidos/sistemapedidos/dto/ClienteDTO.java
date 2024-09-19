package br.com.itilh.bdpedidos.sistemapedidos.dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private BigInteger id;
    private String nomeRazaoSocial;
    private String cnpj;
    private String cpf;
    private String telefone;
    private String endereco;
    private String bairro;
    private String cep;
    private String email;
    private Boolean ativo;
    private String informacoes;

    private BigInteger municipioId;
    private String municipioNome;

}
