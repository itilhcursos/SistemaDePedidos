package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ItemPedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.dto.PedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.model.Pedido;
import br.com.itilh.bdpedidos.sistemapedidos.repository.PedidoRepository;

@Service
public class PedidoService extends GenericService <Pedido,PedidoDTO> {

    @Autowired
    PedidoRepository pedidoRepository;

    public Page<PedidoDTO> getTodos(Pageable pageable ){
        return toPageDTO(pedidoRepository.findAll(pageable));
    }
    public Page<PedidoDTO> listarPedidoPorClienteId(BigInteger id, Pageable pageable) {
        return toPageDTO(pedidoRepository.findByclienteId(id, pageable));
    }
    public Page<PedidoDTO> listarPedidoPorFormaPagamentoId(BigInteger id, Pageable pageable) {
        return toPageDTO(pedidoRepository.findByformaPagamentoId(id, pageable));
    }
    public Page<PedidoDTO> listarPedidoPorItens(List<ItemPedidoDTO> itens, Pageable pageable) {
        return toPageDTO(pedidoRepository.findByitens(itens, pageable));
    }

    public PedidoDTO getPorId(BigInteger id) throws Exception {
        return toDTO(pedidoRepository.findById(id).orElseThrow(
            () -> new Exception("ID inválido.")));
    }  

    private void validar (PedidoDTO dto) throws Exception {

    }

    public PedidoDTO criarPedido(PedidoDTO entityDTO) throws Exception {  
        
        validar(entityDTO);  
        try{    
            return toDTO(pedidoRepository.save(toEntity(entityDTO)));
        }catch(Exception e){
            throw new Exception("Erro ao salvar o pedido.");
        }
    }

    public PedidoDTO alterarPedido(BigInteger id, PedidoDTO novosDados) throws Exception {

        try{     
         return toDTO(pedidoRepository.save(toEntity(novosDados)));
        }catch(Exception e){
            throw new Exception("Alteração não foi realizada.");
        }                                   
    }

    public String deletePorId(BigInteger id) throws Exception {
        pedidoRepository.deleteById(id);
        return "Excluído";
    }  
    
}
