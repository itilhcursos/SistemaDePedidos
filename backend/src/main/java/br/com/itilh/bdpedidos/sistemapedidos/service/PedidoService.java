package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ItemPedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.dto.PedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.NumeroPedidoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.model.ItemPedido;
import br.com.itilh.bdpedidos.sistemapedidos.model.Pedido;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ItemPedidoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.PedidoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;

@Service
public class PedidoService extends GenericService <Pedido,PedidoDTO> {

    @Autowired
    PedidoRepository repositorio;
    
    @Autowired 
    ProdutoRepository produtoRepository;
    
    @Autowired 
    ItemPedidoRepository itemPedidoRepository;

    @Autowired 
    ClienteRepository clienteRepository;

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    public Page<PedidoDTO> getTodos(Pageable pageable ){
        return toPageDTO(repositorio.findAll(pageable));
    }

    public PedidoDTO getPorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id).orElseThrow(
            () -> new Exception("Pedido com ID inválido: " + id)));
    }  

    private Cliente buscarCliente(BigInteger clienteId) throws Exception{
        return clienteRepository.findById(clienteId)
        .orElseThrow(() -> new Exception("Cliente não encontrado com id: " + clienteId));
    }

    private FormaPagamento buscarFormaPagamento(BigInteger formaPagamentoId) throws Exception{
        return formaPagamentoRepository.findById(formaPagamentoId)
        .orElseThrow(() -> new Exception("Forma de pagamento não encontrado com id: " + formaPagamentoId));
    }

    private Produto buscarProduto(BigInteger produtoId) throws Exception{
        return produtoRepository.findById(produtoId)
        .orElseThrow(() -> new Exception("Produto não encontrado com id: " + produtoId));
    }

    private void validar (PedidoDTO dto) throws Exception {
        if (dto.getId() == null) {
            if (repositorio.existsByNumero(dto.getNumero())) {
                throw new NumeroPedidoException(dto.getNumero());
            }
        } else {

            Pedido pedidoExistente = repositorio.findById(dto.getId())
                .orElseThrow(() -> new Exception("Pedido não encontrado com ID: " + dto.getId()));
    
            if (!pedidoExistente.getNumero().equals(dto.getNumero()) &&
                repositorio.existsByNumero(dto.getNumero())) {
                throw new NumeroPedidoException(dto.getNumero());
            }
        }
        buscarCliente(dto.getClienteId());
        buscarFormaPagamento(dto.getFormaPagamentoId());
        validarItens(dto.getItens());

    }

    private void validarItens(List<ItemPedidoDTO> itens) throws Exception {
        for (ItemPedidoDTO item : itens) {
            buscarProduto(item.getProdutoId());
            if (item.getQuantidadeEstoque() <= 0) {
                throw new Exception("A quantidade do produto não pode ser menor ou igual a zero.");
            }
            if (item.getPrecoUnidadeAtual().compareTo(BigDecimal.ZERO) < 0) {
                throw new Exception("O preço unitário do produto não pode ser negativo.");
            }
        }
    }

    public PedidoDTO criarPedido(PedidoDTO novosDados) throws Exception {
        validar(novosDados);
        Pedido pedido = toEntity(novosDados);
        Pedido pedidoSalvo = repositorio.save(pedido);

        try {
            for (ItemPedidoDTO itemDTO : novosDados.getItens()) {
                ItemPedido itemPedido = new ItemPedido();
                itemPedido.setPedido(pedidoSalvo);
                itemPedido.setProduto(buscarProduto(itemDTO.getProdutoId()));
                itemPedido.setQuantidadeEstoque(itemDTO.getQuantidadeEstoque());
                itemPedido.setPrecoUnidadeAtual(itemDTO.getPrecoUnidadeAtual());
                itemPedidoRepository.save(itemPedido);
            }
        } catch (Exception e) {
            throw new Exception("Erro ao salvar os itens do pedido: " + e.getMessage(), e);
        }

        return toDTO(pedidoSalvo);

    }

    public PedidoDTO alterarPedido(BigInteger id, PedidoDTO novosDados) throws Exception {
        validar(novosDados);

    Pedido pedidoExistente = repositorio.findById(id)
            .orElseThrow(() -> new Exception("Pedido não encontrado com ID: " + id));

    // Atualiza o cliente e a forma de pagamento
    pedidoExistente.setCliente(buscarCliente(novosDados.getClienteId()));
    pedidoExistente.setFormaPagamento(buscarFormaPagamento(novosDados.getFormaPagamentoId()));

    // Atualiza as informações gerais do pedido
    pedidoExistente.setDataCompra(novosDados.getDataCompra());
    pedidoExistente.setDataEntrega(novosDados.getDataEntrega());
    pedidoExistente.setDataPagamento(novosDados.getDataPagamento());

    // Atualiza ou adiciona os itens do pedido
    for (ItemPedidoDTO itemDTO : novosDados.getItens()) {
        if (itemDTO.getId() != null) {
            ItemPedido itemExistente = itemPedidoRepository.findById(itemDTO.getId())
                    .orElseThrow(() -> new Exception("Item do Pedido não encontrado com ID: " + itemDTO.getId()));
            atualizarItemPedido(itemExistente, itemDTO);
        } else {
            ItemPedido novoItem = criarNovoItemPedido(pedidoExistente, itemDTO);
            itemPedidoRepository.save(novoItem);
        }
    }

    // Salva o pedido atualizado
    repositorio.save(pedidoExistente);

    return toDTO(pedidoExistente);
}

private void atualizarItemPedido(ItemPedido itemExistente, ItemPedidoDTO itemDTO) {
    itemExistente.setQuantidadeEstoque(itemDTO.getQuantidadeEstoque());
    itemExistente.setPrecoUnidadeAtual(itemDTO.getPrecoUnidadeAtual());
    itemPedidoRepository.save(itemExistente);
}

private ItemPedido criarNovoItemPedido(Pedido pedidoExistente, ItemPedidoDTO itemDTO) throws Exception {
    ItemPedido novoItem = new ItemPedido();
    novoItem.setPedido(pedidoExistente);
    novoItem.setProduto(buscarProduto(itemDTO.getProdutoId()));
    novoItem.setQuantidadeEstoque(itemDTO.getQuantidadeEstoque());
    novoItem.setPrecoUnidadeAtual(itemDTO.getPrecoUnidadeAtual());
    return novoItem;
}

public String deletePorId(BigInteger id) throws Exception {
    Pedido pedido = repositorio.findById(id)
            .orElseThrow(() -> new Exception("Pedido não encontrado com ID: " + id));

    itemPedidoRepository.deleteAll(pedido.getItens());  
    repositorio.delete(pedido);
    return "Pedido excluído com sucesso.";
    }
}
