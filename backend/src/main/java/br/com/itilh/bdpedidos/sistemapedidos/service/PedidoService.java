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
    PedidoRepository repositorio;

    public Page<PedidoDTO> getTodos(Pageable pageable ){
        return toPageDTO(repositorio.findAll(pageable));
    }
    public Page<PedidoDTO> listarPedidoPorClienteId(BigInteger id, Pageable pageable) {
        return toPageDTO(repositorio.findByclienteId(id, pageable));
    }
    public Page<PedidoDTO> listarPedidoPorFormaPagamentoId(BigInteger id, Pageable pageable) {
        return toPageDTO(repositorio.findByformaPagamentoId(id, pageable));
    }
    public Page<PedidoDTO> listarPedidoPorItens(List<ItemPedidoDTO> itens, Pageable pageable) {
        return toPageDTO(repositorio.findByitens(itens, pageable));
    }

    public Page<PedidoDTO> listarPedidoPorClienteNome(String nome, Pageable pageable) {
        return toPageDTO(repositorio.findByclienteNomeIgnoreCase(nome, pageable));
    }
    public Page<PedidoDTO> listarPedidoPorFormaPagamentoNome(String nome, Pageable pageable) {
        return toPageDTO(repositorio.findByformaPagamentoNomeIgnoreCase(nome, pageable));
    }

    public PedidoDTO getPorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id).orElseThrow(
            () -> new Exception("ID inválido.")));
    }  

    private void validar (PedidoDTO dto) throws Exception {

    }

    public PedidoDTO criarPedido(PedidoDTO entityDTO) throws Exception {  
        
        validar(entityDTO);  
        try{    
            return toDTO(repositorio.save(toEntity(entityDTO)));
        }catch(Exception e){
            throw new Exception("Erro ao salvar o pedido.");
        }
    }

    public PedidoDTO alterarPedido(BigInteger id, PedidoDTO novosDados) throws Exception {

        try{     
         return toDTO(repositorio.save(toEntity(novosDados)));
        }catch(Exception e){
            throw new Exception("Alteração não foi realizada.");
        }                                   
    }

    public String deletePorId(BigInteger id) throws Exception {
        repositorio.deleteById(id);
        return "Excluído";
    }  
    
}
