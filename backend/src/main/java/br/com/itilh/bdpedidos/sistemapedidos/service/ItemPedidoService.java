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
public class ItemPedidoService extends GenericService<ItemPedido, ItemPedidoDTO>{

    @Autowired
    ItemPedidoRepository repositorio;

    @Autowired
    ProdutoRepository repositorioProduto;

    public Page<ItemPedidoDTO> getTodos(Pageable pageable ){
        return toPageDTO(repositorio.findAll(pageable));
    }

    public ItemPedidoDTO getPorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id).orElseThrow(
            () -> new Exception("ID inválido.")));
    }  

    private void validar (ItemPedidoDTO dto) throws Exception {

        /* if(dto.getNumero().length() < 3 || dto.getNumero().length() > 50)
            throw new NumeroPedidoInvalidoException(dto.getNumero());
        
        if(repositorio.existsByNumero(dto.getNumero()))   
            throw new ItemPedidoDuplicadoException(dto.getNumero()); */

    }

    @Transactional // anotação que garante que o método só é executado se e somente se todas as diversas operações dentro ocorrerem corretamente
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

        validar(novosDados);
        /* if(repositorio.existsByNumero(novosDados.getNumero()))   
            throw new ItemPedidoDuplicadoException(novosDados.getNumero());
             */
        try{     
         return toDTO(repositorio.save(toEntity(novosDados)));
        }catch(Exception e){
            throw new Exception("Alteração não foi realizada.");
        }                                   
    }

    @Transactional
    public String excluirItemPedido(BigInteger id) throws Exception {
        ItemPedido item = repositorio.getReferenceById(id);
        Produto produto = item.getProduto();
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + item.getQuantidadeEstoque());
        repositorioProduto.save(produto);
        repositorio.deleteById(id);
        return "Excluído";
    }
}
