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
@Table(name = "tb_pedidos")
public class Pedido {

@Id
@SequenceGenerator(name = "sequencial3", sequenceName = "tb_pedidos_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequencial3")
    private BigInteger id;

@ManyToOne
@JoinColumn(name = "id_cliente")
private Cliente cliente;

@ManyToOne
@JoinColumn(name = "id_forma_pagamento")
private FormaPagamento formaPagamento;

@Column(name = "int_numero")
private Integer numero;

@Column(name = "dt_compra")
private String compra;

@Column(name = "dt_entrega")
private String entrega;

@Column(name = "dt_pagamento")
private String pagamento;

}
