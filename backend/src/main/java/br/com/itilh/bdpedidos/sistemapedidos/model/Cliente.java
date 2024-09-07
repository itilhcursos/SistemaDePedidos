package br.com.itilh.bdpedidos.sistemapedidos.model;

import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
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
@Entity
@Table(name = "tb_clientes")
public class Cliente {

    @Id
    @SequenceGenerator(name = "sequencial", sequenceName = "tb_clientes_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequencial")
    private BigInteger id;

    @ManyToOne
    @JoinColumn(name = "id_municipio")
    private Municipio municipio;

    @Column(name="tx_nome_razao_social")
    private String nomeRazaoSocial;

    @Column(name="tx_cnpj")
    private String cnpj;
    
    @Column(name="tx_cpf")
    private String cpf;

    @Column(name="tx_telefone")
    private String telefone;

    @Column(name="tx_endereco")
    private String endereco;

    @Column(name="tx_bairro")
    private String bairro;

    @Column(name="tx_cep")
    private String cep;

    @Column(name="tx_email")
    private String email;

    @Column(name="bo_ativo")
    private boolean ativo;

    @Column(name="tx_informacoes")
    private String informacao;


}
