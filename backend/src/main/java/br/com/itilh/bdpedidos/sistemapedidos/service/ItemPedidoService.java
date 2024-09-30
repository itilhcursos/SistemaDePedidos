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

    public ItemPedidoDTO criarItemPedido(ItemPedidoDTO entityDTO) throws Exception {  
        
        validar(entityDTO);  
        try{    
            return toDTO(repositorio.save(toEntity(entityDTO)));
        }catch(Exception e){
            throw new Exception("Erro ao salvar o pedido.");
        }
    }

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

    public String excluirItemPedido(BigInteger id) throws Exception {
        repositorio.deleteById(id);
        return "Excluído";
    }
}
