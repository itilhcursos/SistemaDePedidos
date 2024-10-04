package br.com.itilh.bdpedidos.sistemapedidos.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    @SequenceGenerator(name = "sequencial", sequenceName = "tb_pedidos_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequencial")
    private BigInteger id;
    @Column(name = "int_numero")
    private BigInteger numero;
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "id_forma_pagamento")
    private FormaPagamento formaPagamento;
    @Column(name = "dt_compra")
    private LocalDate dataCompra;
    @Column(name = "dt_entrega")
    private LocalDate dataEntrega;

    @Column(name = "dt_pagamento")
    private LocalDate dataPagamento;
    @OneToMany(mappedBy = "pedido", fetch = FetchType.LAZY)
    private List<ItemPedido> itens;
}