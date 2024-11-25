package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ItemPedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ItemPedidoPedidoIdDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ItemPedidoEstoqueNegativoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ItemPedidoPrecoNegativoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ItemPedidoProdutoIdDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.ItemPedido;

import br.com.itilh.bdpedidos.sistemapedidos.repository.ItemPedidoRepository;

@Service
public class ItemPedidoService extends GenericService<ItemPedido, ItemPedidoDTO> {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    public Page<ItemPedidoDTO> listarItemPedidos(Pageable pageable) {
        return toPageDTO(itemPedidoRepository.findAll(pageable));
    }

    public Page<ItemPedidoDTO> listarItemPedidoPorPedidoId(BigInteger id, Pageable pageable) {
        return toPageDTO(itemPedidoRepository.findByPedidoId(id, pageable));
    }

    public Page<ItemPedidoDTO> listarItemPedidoPorProdutoId(BigInteger id, Pageable pageable) {
        return toPageDTO(itemPedidoRepository.findByProdutoId(id, pageable));
    }

    public Page<ItemPedidoDTO> listarItemPedidoPorProdutoDescricao(String descricao, Pageable pageable) {
        return toPageDTO(itemPedidoRepository.findByProduto_DescricaoIgnoreCase(descricao, pageable));
    }

    public ItemPedidoDTO buscarItemPedidoPorId(BigInteger id) throws Exception {
        return toDTO(itemPedidoRepository.findById(id)
                .orElseThrow(() -> new IdInexistenteException("ItemPedido", id)));
    }

    private void validar(ItemPedidoDTO origem) {
        if (itemPedidoRepository.existsByPedidoId(origem.getPedidoId()))
            throw new ItemPedidoPedidoIdDuplicadoException(origem.getPedidoId());
        if (itemPedidoRepository.existsByQuantidadeEstoque(origem.getQuantidadeEstoque()))
            throw new ItemPedidoEstoqueNegativoException(origem.getQuantidadeEstoque());
        if (itemPedidoRepository.existsByPrecoUnidadeAtual(origem.getPrecoUnidadeAtual()))
            throw new ItemPedidoPrecoNegativoException(origem.getPrecoUnidadeAtual());
        if (itemPedidoRepository.existsByProdutoId(origem.getProdutoId()))
            throw new ItemPedidoProdutoIdDuplicadoException(origem.getProdutoId());
    }

    public ItemPedidoDTO criarItemPedido(ItemPedidoDTO origem) throws Exception {
        validar(origem);
        return toDTO(itemPedidoRepository.save(toEntity(origem)));

    }

    public ItemPedidoDTO alterarItemPedido(BigInteger id, ItemPedidoDTO origem) throws Exception {
        validar(origem);
        return toDTO(itemPedidoRepository.save(toEntity(origem)));
    }

    public String excluirItemPedido(BigInteger id) throws Exception {
        try {
            itemPedidoRepository.deleteById(id);
            return "Excluído com sucesso";
        } catch (Exception ex) {
            throw new Exception("Não foi possível excluir o ID solicitado." + ex.getMessage());
        }
    }

    public Object testAlterarItemPedidoPrecoNegativo(BigDecimal precoUnidadeAtual, ItemPedidoDTO origem) {
        validar(origem);
        return toDTO(itemPedidoRepository.save(toEntity(origem)));
    }

    public Object alterarItemPedidoEstoqueNegativo(Double quantidadeEstoque, ItemPedidoDTO origem) {
        validar(origem);
        return toDTO(itemPedidoRepository.save(toEntity(origem)));
    }

}
