package br.com.itilh.bdpedidos.sistemapedidos.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString

@Entity
@Table(name="tb_pedidos")
public class Pedido {

    @Id
    @SequenceGenerator(name="sequencial", sequenceName="tb_pedidos_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="sequencial")
    private BigInteger id;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne // analisar se esta certo - se é OneToMany ou ManyToOne ou ManyToMany???? // é ManyToOne
    @JoinColumn(name = "id_forma_pagamento")
    private FormaPagamento formaPagamento;

    @Column(name="int_numero")
    private BigInteger numero;

    @Column(name="dt_compra")
    private LocalDate dataCompra;

    @Column(name="dt_entrega")
    private LocalDate dataEntrega;
    
    @Column(name="dt_pagamento")
    private LocalDate dataPagamento;

    @OneToMany(mappedBy="pedido")
    private List<ItemPedido> itens;

    //@OneToMany(mappedBy="pedido")
    //private List<ItemPedido> itens;
    // essa é a anotação que liga com a classe de Objeto ItemPedido 
    //onde ele mapeia usando o mappedBy os itens de pedido e todos os itens de pedidos 
    // que tiverem o mesmo id que o pedido ele ira trazer 

    // mas para isso o ItemPedido deve ter uma chave strangeira ligada 
    //no caso é o id_pedido
    //@ManyToOne
    //@JoinColumn(name="id_pedido")
    //private Pedido pedido; 
    //isso é conhecido como mapeamento Bidirecional ou seja tem tanto de um lado quanto do outro
    //um está mapeando o outro
    
}