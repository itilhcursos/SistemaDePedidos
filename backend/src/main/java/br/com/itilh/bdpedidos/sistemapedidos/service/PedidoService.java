package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.itilh.bdpedidos.sistemapedidos.dto.PedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.PedidoDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.ItemPedido;
import br.com.itilh.bdpedidos.sistemapedidos.model.Pedido;
import br.com.itilh.bdpedidos.sistemapedidos.repository.PedidoRepository;

@Service
public class PedidoService extends GenericService<Pedido, PedidoDTO>{

    @Autowired
    PedidoRepository repositorio;

    @Autowired
    ItemPedidoService itemService;

    public Page<PedidoDTO> getTodos(Pageable pageable ){
        return toPageDTO(repositorio.findAll(pageable));
    }

    public PedidoDTO getPorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id).orElseThrow(
            () -> new Exception("ID inválido.")));
    }  

    private void validar (PedidoDTO dto) throws Exception {

        if (repositorio.existsByNumero(dto.getNumero())){
            if(dto.getId() == null){
                throw new PedidoDuplicadoException(dto.getNumero());
            }else{
                // Alterando um Pedido
                Pedido p = repositorio.getReferenceById(dto.getId());
                if(p.getNumero() != (dto.getNumero())){
                    throw new PedidoDuplicadoException(dto.getNumero());
                }
            }            
        }
    }

    public PedidoDTO criarPedido(PedidoDTO entityDTO) throws Exception { 
        validar(entityDTO);
        return toDTO(repositorio.save(toEntity(entityDTO)));
    }

    public PedidoDTO alterarPedido(BigInteger id, PedidoDTO entityDTO) throws Exception {
        validar(entityDTO);    
        return toDTO(repositorio.save(toEntity(entityDTO)));                                  
    }

    @Transactional
    public String excluirPedido(BigInteger id) throws Exception {
        Pedido pedido = repositorio.getReferenceById(id);
        for (ItemPedido item:pedido.getItens()){
            itemService.excluirItemPedido(item.getId());
        }

        repositorio.deleteById(id);
        return "Excluído";
    }  
}
