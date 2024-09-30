package br.com.itilh.bdpedidos.sistemapedidos.model;

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
import jakarta.persistence.Table;
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

@Entity
@Table(name="tb_itens_pedidos")
public class ItemPedido {

    @Id
    @SequenceGenerator(name="sequencial", sequenceName="tb_itens_pedidos_id_seq", allocationSize= 1)
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator="sequencial")
    private BigInteger id;

    @ManyToOne
    @JoinColumn(name="id_pedido")
    private Pedido pedido; // O Id do pedido liga o item ao pedido que ele pertence

    @ManyToOne
    @JoinColumn(name="id_produto")
    private Produto produto;// O Id do Produto diz qual é o produto que está vendido, que pertence a quele item

    @Column(name="dbl_quantidade_estoque")
    private Double quantidadeEstoque;
    
    @Column(name="nu_preco_unidade_atual")
    private BigDecimal precoUnidadeAtual;

}
