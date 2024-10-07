package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ItemPedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.dto.PedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.PedidoDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Pedido;
import br.com.itilh.bdpedidos.sistemapedidos.repository.PedidoRepository;
@Service
public class PedidoService extends GenericService<Pedido,PedidoDTO> {


    @Autowired
    PedidoRepository pedidoRepository;



    public Page<PedidoDTO> listarPedidos(Pageable pageable){
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

    public Page<PedidoDTO> listarPedidoPorClienteNome(String nome, Pageable pageable) {
        return toPageDTO(pedidoRepository.findByclienteNomeIgnoreCase(nome, pageable));
    }
    public Page<PedidoDTO> listarPedidoPorFormaPagamentoNome(String nome, Pageable pageable) {
        return toPageDTO(pedidoRepository.findByformaPagamentoNomeIgnoreCase(nome, pageable));
    }
    

     public PedidoDTO buscarPedidoPorId(BigInteger id) throws Exception {
        return toDTO(pedidoRepository.findById(id)
        .orElseThrow(()-> new IdInexistenteException("Pedido", id)));
    }
    public PedidoDTO criarPedido(PedidoDTO origem) throws Exception {    
       validar(origem);
        return toDTO(pedidoRepository.save(toEntity(origem)));
    }
public PedidoDTO alterarPedido (BigInteger id, PedidoDTO origem) throws Exception {
        validar(origem);
    return toDTO(pedidoRepository.save(toEntity(origem)));
    }

    public String excluirPedido(BigInteger id) throws Exception{
        try{ 
            pedidoRepository.deleteById(id);
             return "Excluído com sucesso";
        }catch (Exception ex){
            throw new Exception("Não foi possível excluir o id informado." + ex.getMessage());
        }
    }

private void validar(PedidoDTO origem){
    if(pedidoRepository.existsByNumero(origem.getNumero()))
    throw new PedidoDuplicadoException(origem.getNumero()); 
}

}
