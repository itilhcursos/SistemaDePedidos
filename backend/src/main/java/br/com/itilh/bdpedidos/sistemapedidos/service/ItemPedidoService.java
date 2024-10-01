package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ItemPedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.model.ItemPedido;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ItemPedidoRepository;

@Service
public class ItemPedidoService extends GenericService<ItemPedido, ItemPedidoDTO> {

    @Autowired
    private ItemPedidoRepository repositorio;

    public ItemPedidoDTO criarItemPedido(ItemPedidoDTO entityDTO) throws Exception {
        return toDTO(repositorio.save(toEntity(entityDTO)));
    }

    public String excluirItemPedido(BigInteger id) throws Exception{
        try{ 
            repositorio.deleteById(id);
             return "Excluído";
        }catch (Exception ex){
            throw new Exception("Não foi possível excluir o id informado." + ex.getMessage());
        }
    }

}
