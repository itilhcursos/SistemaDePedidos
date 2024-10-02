package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

/* import java.math.BigInteger; */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ItemPedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoDuplicadoException;
/* import br.com.itilh.bdpedidos.sistemapedidos.exception.ProdutoEstoqueNegativoException; */
import br.com.itilh.bdpedidos.sistemapedidos.model.ItemPedido;
/* import br.com.itilh.bdpedidos.sistemapedidos.model.Produto; */
import br.com.itilh.bdpedidos.sistemapedidos.repository.ItemPedidoRepository;
/* import br.com.itilh.bdpedidos.sistemapedidos.repository.ProdutoRepository;
import jakarta.transaction.Transactional; */

@Service
public class ItemPedidoService extends GenericService<ItemPedido, ItemPedidoDTO> {

    @Autowired
    ItemPedidoRepository repository;

    public Page<ItemPedidoDTO> listarItemPedidos(Pageable pageable) {
        return toPageDTO(repository.findAll(pageable));
    }

    public Page<ItemPedidoDTO> listarItemPedidosPorPedidoId(BigInteger id, Pageable pageable) {
        return toPageDTO(repository.findByPedidoId(id, pageable));
    }

    public Page<ItemPedidoDTO> listarItemPedidoPorProdutoNome(String nome, Pageable pageable) {
        return toPageDTO(repository.findByProdutoNomeIgnoreCase(nome, pageable));
    }

    public ItemPedidoDTO buscarItemPedidoPorId(BigInteger id) throws Exception {
        return toDTO(repository.findById(id)
        .orElseThrow(()-> new IdInexistenteException("ItemPedido", id)));
    }

    public ItemPedidoDTO criarItemPedido(ItemPedidoDTO origem) throws Exception {
        validar(origem);
        return toDTO(repository.save(toEntity(origem)));
    }

    private void validar(ItemPedidoDTO origem) {
        if (repository.existsByDescricaoAndProdutoId(origem.getDescricao(), origem.getProdutoId())) {
            throw new ProdutoDuplicadoException(origem.getDescricao());
        }
    }

    public ItemPedidoDTO alterarItemPedido(BigInteger id, ItemPedidoDTO origem) throws Exception {
        validar(origem);
        return toDTO(repository.save(toEntity(origem)));
    }

    public String excluirItemPedido(BigInteger id) throws Exception {
        try{ 
            repository.deleteById(id);
             return "Excluído com sucesso";
        }catch (Exception ex){
            throw new Exception("Não foi possível excluir o id informado." + ex.getMessage());
        }
    }
}

/* @Autowired
    ProdutoRepository repositorioProduto; */

    /* @Transactional
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
    } */

    /* @Transactional
    public String deletePorId(BigInteger id) throws Exception {

        ItemPedido item = repositorio.getReferenceById(id);
        Produto produto = item.getProduto();
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + item.getQuantidadeEstoque());
        repositorioProduto.save(produto);
        repositorio.deleteById(id);
        return "Excluído";
    } */