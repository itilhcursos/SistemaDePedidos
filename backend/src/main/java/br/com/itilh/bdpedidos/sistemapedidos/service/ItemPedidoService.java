package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ItemPedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoEstoqueNegativoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.ItemPedido;
import br.com.itilh.bdpedidos.sistemapedidos.model.Produto;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ItemPedidoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;
import jakarta.transaction.Transactional;

@Service
public class ItemPedidoService extends GenericService<ItemPedido, ItemPedidoDTO>{

    @Autowired
    ItemPedidoRepository repositorio;

    @Autowired
    ProdutoRepository repositorioProduto;

    @Transactional
    public ItemPedidoDTO criarItemPedido(ItemPedidoDTO dto) throws Exception {  
        
        Produto produto = repositorioProduto.getReferenceById(dto.getProdutoId());
        if(produto!= null 
            && produto.getQuantidadeEstoque() != null 
            && dto.getQuantidadeEstoque() > 0 
            && ((produto.getQuantidadeEstoque() - dto.getQuantidadeEstoque()) >=0 )){

                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - dto.getQuantidadeEstoque());
                repositorioProduto.save(produto);

                dto.setPrecoUnidadeAtual(produto.getPrecoUnidadeAtual());
                return toDTO(repositorio.save(toEntity(dto)));
        }
        throw new ProdutoEstoqueNegativoException(dto.getProdutoDescricao());
    }

    @Transactional
    public ItemPedidoDTO alterarItemPedido(BigInteger id, ItemPedidoDTO dto) throws Exception {

        try{     
         return toDTO(repositorio.save(toEntity(dto)));
        }catch(Exception e){
            throw new Exception("A alteração não foi realizada.");
        }                                   
    }

    @Transactional
    public String deleteItemPedido(BigInteger id) throws Exception {

        ItemPedido item = repositorio.getReferenceById(id);
        Produto produto = item.getProduto();
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + item.getQuantidadeEstoque());
        repositorioProduto.save(produto);
        repositorio.deleteById(id);
        return "O registro foi excluído";
    }
}