package br.com.itilh.bdpedidos.sistemapedidos.model;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
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
@Table (name= "tb_itens_pedidos")
public class ItemPedido {

@Id
@SequenceGenerator(name = "sequencial2", sequenceName = "tb_itens_pedidos_id_seq", allocationSize = 1 )
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequencial2")
private BigInteger id;

@ManyToOne
@JoinColumn(name = "id_pedido")
private Pedido pedido;

@ManyToOne
@JoinColumn(name = "id_produto")
private Produto produto;

@Column(name = "dbl_quantidade_estoque")
private Double quantidadeEstoque;

@Column(name = "nu_preco_unidade_atual")
private BigDecimal precoUnidadeAtual;



}
