package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import br.com.itilh.bdpedidos.sistemapedidos.dto.ItemPedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.ItemPedido;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ItemPedidoRepository;

public class ItemPedidoService extends GenericService<ItemPedido,ItemPedidoDTO> {

@Autowired
private ItemPedidoRepository itemPedidoRepository;

public Page<ItemPedidoDTO> listaritemPedidos(Pageable pageable){
     return toPageDTO(itemPedidoRepository.findAll(pageable));
}

 public Page<ItemPedidoDTO> listarItemPedidoPorPedidoId(BigInteger id, Pageable pageable) {
        return toPageDTO(itemPedidoRepository.findBypedidoId(id, pageable));
    }
    public Page<ItemPedidoDTO> listarItemPedidoPorProdutoId(BigInteger id, Pageable pageable) {
        return toPageDTO(itemPedidoRepository.findByprodutoId(id, pageable));
    }

    public Page<ItemPedidoDTO> listarItemPedidoPorProdutoNome(String nome, Pageable pageable) {
        return toPageDTO(itemPedidoRepository.findByprodutoNomeIgnoreCase(nome, pageable));
    }
     public ItemPedidoDTO buscarItemPEdidoPorId(BigInteger id) throws Exception {
        return toDTO(itemPedidoRepository.findById(id)
        .orElseThrow(()-> new IdInexistenteException("ItemPedido", id)));
    }
    public ItemPedidoDTO criarMunicipio(ItemPedidoDTO origem) throws Exception {    
        return toDTO(itemPedidoRepository.save(toEntity(origem)));
    }
public ItemPedidoDTO alterarItemPedido (BigInteger id, ItemPedidoDTO origem) throws Exception {
        return toDTO(itemPedidoRepository.save(toEntity(origem)));
    }

    public String excluirItemPedido(BigInteger id) throws Exception{
        try{ 
            itemPedidoRepository.deleteById(id);
             return "Excluído com sucesso";
        }catch (Exception ex){
            throw new Exception("Não foi possível excluir o id informado." + ex.getMessage());
        }
    }

}
