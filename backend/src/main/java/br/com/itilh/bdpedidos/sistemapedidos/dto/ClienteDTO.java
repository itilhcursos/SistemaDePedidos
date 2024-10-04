package br.com.itilh.bdpedidos.sistemapedidos.dto;

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

public class ClienteDTO {

    private BigInteger id;

    private BigInteger municipioId;

    private String municipioNome;

    private String municipioEstadoId;

    private String municipioEstadoNome;

    private String nomeRazaoSocial;

    private String cnpj;

    private String cpf;

    private String telefone;

    private String endereco;

    private String bairro;

    private String cep;

    private String email;

    private boolean ativo;

    private String informacao;

}
