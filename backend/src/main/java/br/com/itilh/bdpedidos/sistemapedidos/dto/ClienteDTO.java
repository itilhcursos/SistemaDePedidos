package br.com.itilh.bdpedidos.sistemapedidos.dto;

import java.math.BigInteger;

import br.com.itilh.bdpedidos.sistemapedidos.model.Municipio;
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

    private Municipio municipio;

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
