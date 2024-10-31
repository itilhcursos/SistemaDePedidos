package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ItemPedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoEstoqueNegativoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.ItemPedido;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ItemPedidoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;
import jakarta.transaction.Transactional;

@Service
public class ItemPedidoService extends GenericService<ItemPedido, ItemPedidoDTO> {

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    ProdutoRepository produtoRepository;

// LISTAGENS (GET) //

    // Listar todos os itens do pedido
    public Page<ItemPedidoDTO> listarItensPedido(Pageable pageable) {
        return toPageDTO(itemPedidoRepository.findAll(pageable));
    }

    // Buscar produto pelo ID, para que venha ser um item do pedido
    public ItemPedidoDTO buscarProdutoPorId(BigInteger id) throws Exception {
        return toDTO(itemPedidoRepository.findById(id).orElseThrow(() -> new IdInexistenteException("Produto", id)));
    }

    // Criar registro de item no pedido
    @Transactional
    public ItemPedidoDTO criarItemPedido(ItemPedidoDTO entityDTO) throws Exception { 
        Produto produto = produtoRepository.getReferenceById(entityDTO.getProdutoId());
        if (produto!= null && 
            produto.getQuantidadeEstoque() != null && 
            entityDTO.getQuantidadeEstoque() >0 &&
            ((produto.getQuantidadeEstoque() - entityDTO.getQuantidadeEstoque()) >=0 )){

                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - entityDTO.getQuantidadeEstoque());
                produtoRepository.save(produto);
                entityDTO.setPrecoUnidadeAtual(produto.getPrecoUnidadeAtual());
                return toDTO(itemPedidoRepository.save(toEntity(entityDTO)));
        }
        throw new ProdutoEstoqueNegativoException(entityDTO.getProdutoDescricao());
    }
    
    // Excluir registro de item no pedido
    @Transactional
    public String deletePorId(BigInteger id) throws Exception {
        ItemPedido item = itemPedidoRepository.getReferenceById(id);
        Produto produto = item.getProduto();
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + item.getQuantidadeEstoque());
        produtoRepository.save(produto);
        itemPedidoRepository.deleteById(id);
        return "Excluído";
    }  

}
