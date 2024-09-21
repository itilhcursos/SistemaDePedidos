package br.com.itilh.bdpedidos.sistemapedidos.dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

    private BigInteger id;
    private BigInteger id_municipio;
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

}
