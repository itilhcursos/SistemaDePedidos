package br.com.itilh.bdpedidos.sistemapedidos.model;

import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "tb_formas_pagamento")

public class FormaPagamento {
    @Id
    @SequenceGenerator(name = "formaPagamento_id_seq", sequenceName = "tb_formas_pagamento_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "formaPagamento_id_seq")
    private BigInteger id;

    @Column(name = "tx_descricao")
    private String descricao;

    @Column(name = "bo_ativo")
    private Boolean ativo;
}
