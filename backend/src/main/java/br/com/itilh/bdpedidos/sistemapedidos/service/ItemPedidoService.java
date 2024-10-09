package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ItemPedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.model.ItemPedido;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ItemPedidoRepository;

@Service
public class ItemPedidoService extends GenericService<ItemPedido, ItemPedidoDTO>{

    @Autowired
    ItemPedidoRepository repositorio;

    public Page<ItemPedidoDTO> listarItensPedido(Pageable pageable ){
        return toPageDTO(repositorio.findAll(pageable));
    }

    public ItemPedidoDTO criarItemPedido(ItemPedidoDTO entityDTO) throws Exception {  
        return toDTO(repositorio.save(toEntity(entityDTO))); 
    }

    public String deletePorId(BigInteger id) throws Exception {
        repositorio.deleteById(id);
        return "Excluído";
    }  
}
