package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ItemPedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoEstoqueNegativoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.ItemPedido;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ItemPedidoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;
import jakarta.transaction.Transactional;

@Service
public class ItemPedidoService extends GenericService<ItemPedido, ItemPedidoDTO> {

    @Autowired
    ItemPedidoRepository repositorio;

    @Autowired
    ProdutoRepository repositorioProduto;

    public Page<ItemPedidoDTO> getTodos(Pageable pageable) {
        return repositorio.findAll(pageable).map(this::toDTO);
    }

    public Page<ItemPedidoDTO> listarItensPorPedidoId(BigInteger pedidoId, Pageable pageable) {
        return repositorio.findByPedidoId(pedidoId, pageable).map(this::toDTO);
    }

    @Transactional
    public ItemPedidoDTO getPorId(BigInteger id) throws Exception {
    return repositorio.findById(id)
            .map(this::toDTO)
            .orElseThrow(() -> new Exception("ItemPedido não encontrado com o ID: " + id));
}

    @Transactional
    public ItemPedidoDTO criarItemPedido(ItemPedidoDTO entityDTO) throws Exception { 

        Produto produto = repositorioProduto.getReferenceById(entityDTO.getProdutoId());
        if(produto!= null && produto.getQuantidadeEstoque() != null
            && entityDTO.getQuantidadeEstoque() >0 &&
            ((produto.getQuantidadeEstoque() - entityDTO.getQuantidadeEstoque())>=0)){

                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - entityDTO.getQuantidadeEstoque());
                repositorioProduto.save(produto);

                entityDTO.setPrecoUnidadeAtual(produto.getPrecoUnidadeAtual());
                return toDTO(repositorio.save(toEntity(entityDTO)));
        }
        throw new ProdutoEstoqueNegativoException(entityDTO.getProdutoDescricao());
        
    }

    @Transactional
    public ItemPedidoDTO alterarItemPedido(BigInteger id, ItemPedidoDTO novosDados) throws Exception {

        try{     
         return toDTO(repositorio.save(toEntity(novosDados)));
        }catch(Exception e){
            throw new Exception("Alteração não foi realizada.");
        }                                   
    }

    @Transactional
    public String deletePorId(BigInteger id) throws Exception {

        ItemPedido item = repositorio.getReferenceById(id);
        Produto produto = item.getProduto();
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + item.getQuantidadeEstoque());
        repositorioProduto.save(produto);
        repositorio.deleteById(id);
        return "Excluído";
    }  


}
