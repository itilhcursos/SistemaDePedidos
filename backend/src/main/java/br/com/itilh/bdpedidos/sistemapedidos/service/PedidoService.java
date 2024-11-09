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
import br.com.itilh.bdpedidos.sistemapedidos.exception.PedidoNumeroException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Pedido;
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.model.ItemPedido;
import br.com.itilh.bdpedidos.sistemapedidos.repository.PedidoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ItemPedidoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;

@Service
public class PedidoService extends GenericService<Pedido, PedidoDTO> {

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    FormaPagamentoRepository formaPagamentoRepository;

    @Autowired
    ProdutoRepository produtoRepository;

    @Autowired
    ItemPedidoRepository itemPedidoRepository;

// LISTAGENS (GET) //

    // Buscar todos os pedidos
    public Page<PedidoDTO> getTodos(Pageable pageable) {
        return toPageDTO(pedidoRepository.findAll(pageable));
    }

    // Buscar pedido por ID
    public PedidoDTO getPorId(BigInteger id) throws Exception {
        return toDTO(pedidoRepository.findById(id).orElseThrow(() -> new Exception("Pedido com ID inválido: " + id)));
    }

    // Buscar cliente pelo ID
    private Cliente buscarCliente(BigInteger clienteId) throws Exception {
        return clienteRepository.findById(clienteId).orElseThrow(() -> new Exception("Cliente não encontrado com ID: " + clienteId));
    }

    // Buscar forma de pagamento pelo ID
    private FormaPagamento buscarFormaPagamento(BigInteger formaPagamentoId) throws Exception {
        return formaPagamentoRepository.findById(formaPagamentoId).orElseThrow(() -> new Exception("Forma de pagamento não encontrada com ID: " + formaPagamentoId));
    }

    // Buscar produto pelo ID
    private Produto buscarProduto(BigInteger produtoId) throws Exception {
        return produtoRepository.findById(produtoId).orElseThrow(() -> new Exception("Produto não encontrado com ID: " + produtoId));
    }

// POST - PUT - DELETE //

    // Criar novo registro de pedido
    public PedidoDTO criarPedido(PedidoDTO novosDados) throws Exception {
        validar(novosDados);
        try {
            Pedido pedido = toEntity(novosDados);  
            Pedido pedidoSalvo = pedidoRepository.save(pedido);  
    
            for (ItemPedidoDTO itemDTO : novosDados.getItens()) {
                ItemPedido itemPedido = new ItemPedido();
                itemPedido.setPedido(pedidoSalvo);  
                itemPedido.setProduto(buscarProduto(itemDTO.getProdutoId()));  
                itemPedido.setQuantidadeEstoque(itemDTO.getQuantidadeEstoque());
                itemPedido.setPrecoUnidadeAtual(itemDTO.getPrecoUnidadeAtual());
                itemPedidoRepository.save(itemPedido);
            }
            return toDTO(pedidoSalvo);  
        } catch (Exception e) {
            throw new Exception("Erro ao salvar o pedido e os itens.");
        }
    }

    // Alterar registro já existente de pedido
    public PedidoDTO alterarPedido(BigInteger id, PedidoDTO novosDados) throws Exception {
        validar(novosDados);
        Pedido pedidoExistente = pedidoRepository.findById(id).orElseThrow(() -> new Exception("Pedido não encontrado com ID: " + id));
        Cliente cliente = clienteRepository.findById(novosDados.getClienteId()).orElseThrow(() -> new Exception("Cliente não encontrado com ID: " + novosDados.getClienteId()));
        FormaPagamento formaPagamento = formaPagamentoRepository.findById(novosDados.getFormaPagamentoId()).orElseThrow(() -> new Exception("Forma de pagamento não encontrada com ID: " + novosDados.getFormaPagamentoId()));
        pedidoExistente.setCliente(cliente);
        pedidoExistente.setFormaPagamento(formaPagamento);
        pedidoExistente.setDataCompra(novosDados.getDataCompra());
        pedidoExistente.setDataEntrega(novosDados.getDataEntrega());
        pedidoExistente.setDataPagamento(novosDados.getDataPagamento());
    
        for (ItemPedidoDTO itemDTO : novosDados.getItens()) {
            if (itemDTO.getId() != null) {
                ItemPedido itemExistente = itemPedidoRepository.findById(itemDTO.getId()).orElseThrow(() -> new Exception("Item do Pedido não encontrado com ID: " + itemDTO.getId()));
                itemExistente.setQuantidadeEstoque(itemDTO.getQuantidadeEstoque());
                itemExistente.setPrecoUnidadeAtual(itemDTO.getPrecoUnidadeAtual());
                itemPedidoRepository.save(itemExistente);
            } else {
                ItemPedido novoItem = new ItemPedido();
                novoItem.setPedido(pedidoExistente);
                novoItem.setProduto(buscarProduto(itemDTO.getProdutoId()));
                novoItem.setQuantidadeEstoque(itemDTO.getQuantidadeEstoque());
                novoItem.setPrecoUnidadeAtual(itemDTO.getPrecoUnidadeAtual());
                itemPedidoRepository.save(novoItem);
            }
        }
        pedidoRepository.save(pedidoExistente);
        return toDTO(pedidoExistente);
    }
    
    // Excluir registro de pedido e itens vinculados
    public String deletePorId(BigInteger id) throws Exception {
        Pedido pedido = pedidoRepository.findById(id).orElseThrow(() -> new Exception("Pedido não encontrado com ID: " + id));
        itemPedidoRepository.deleteAll(pedido.getItens());
        pedidoRepository.delete(pedido);
        return "Pedido excluído com sucesso.";
    }

// VALIDAÇÕES //

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

    private void validar(PedidoDTO dto) throws Exception {
        if (dto.getId() == null) {
            if (pedidoRepository.existsByNumero(dto.getNumero())) {
                throw new PedidoNumeroException(dto.getNumero());
            }
        } else {
            Pedido pedidoExistente = pedidoRepository.findById(dto.getId()).orElseThrow(() -> new Exception("Pedido não encontrado com ID: " + dto.getId()));
            if (!pedidoExistente.getNumero().equals(dto.getNumero()) &&
                pedidoRepository.existsByNumero(dto.getNumero())) {
                throw new PedidoNumeroException(dto.getNumero());
            }
        }
        buscarCliente(dto.getClienteId());
        buscarFormaPagamento(dto.getFormaPagamentoId());
        validarItens(dto.getItens());
    }
}
