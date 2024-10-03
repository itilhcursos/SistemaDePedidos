package br.com.itilh.bdpedidos.sistemapedidos.dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
<<<<<<< HEAD
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private boolean ativo;
    private String informacao;

    private BigInteger municipioId;
    private String municipioNome;
=======
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
    private BigInteger municipioId;
    private String municipioNome;
    private String nomeRazaoSocial;
    private String cnpj;   
    private String cpf;    
    private String telefone;    
    private String endereco;   
    private String bairro;  
    private String cep;    
    private String email;   
    boolean ativo;    
    private String informacao;
>>>>>>> developer
}
