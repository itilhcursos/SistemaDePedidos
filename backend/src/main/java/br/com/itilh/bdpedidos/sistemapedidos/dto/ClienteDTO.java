package br.com.itilh.bdpedidos.sistemapedidos.dto;

import java.math.BigInteger;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
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
    private String informacao;

}