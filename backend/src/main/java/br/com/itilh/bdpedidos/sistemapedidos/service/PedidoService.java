package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.EstadoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.dto.PedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.EstadoDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.NomeEstadoInvalidoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Pedido;
import br.com.itilh.bdpedidos.sistemapedidos.repository.EstadoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.repository.PedidoRepository;

@Service
public class PedidoService  extends GenericService<Pedido, PedidoDTO> {


// FALTA ALTERAR
    @Autowired
    PedidoRepository repositorio;

    public Page<PedidoDTO> getTodos(Pageable pageable ){
        return toPageDTO(repositorio.findAll(pageable));
    }

    public PedidoDTO getPorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id).orElseThrow(
            () -> new Exception("ID inválido.")));
    }  

    private void validar (PedidoDTO dto) throws Exception {

        // if(dto.getNome().length() < 3 || dto.getNome().length() > 50)
        //     throw new NomeEstadoInvalidoException(dto.getNome());
        
        // if(repositorio.existsByNome(dto.getNome()))   
        //     throw new EstadoDuplicadoException(dto.getNome());

    }

    public PedidoDTO criarPedido(PedidoDTO entityDTO) throws Exception {  
        
        validar(entityDTO);  
        try{    
            return toDTO(repositorio.save(toEntity(entityDTO)));
        }catch(Exception e){
            throw new Exception("Erro ao salvar o pedido.");
        }
    }

    // public PedidoDTO alterarPedido(BigInteger id, PedidoDTO novosDados) throws Exception {

    //     validar(novosDados);
    //     if(repositorio.existsByNome(novosDados.getNome()))   
    //         throw new EstadoDuplicadoException(novosDados.getNome());
            
    //     try{     
    //      return toDTO(repositorio.save(toEntity(novosDados)));
    //     }catch(Exception e){
    //         throw new Exception("Alteração não foi realizada.");
    //     }                                   
    // }

    public String deletePorId(BigInteger id) throws Exception {
        repositorio.deleteById(id);
        return "Excluído";
    }  

}
