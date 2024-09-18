package br.com.itilh.bdpedidos.sistemapedidos.service;


import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ClienteDTO;
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;

@Service
public class ClienteService extends GenericService<Cliente,ClienteDTO>{

        @Autowired
        ClienteRepository repositorio;

        public Page<ClienteDTO> getTodos(Pageable pageable ){
        return toPageDTO(repositorio.findAll(pageable));
        }

    public ClienteDTO getPorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id).orElseThrow(
            () -> new Exception("ID inválido.")));
    }

    public ClienteDTO criarCliente(ClienteDTO entityDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'criarCliente'");
    }

    public ClienteDTO alterarCliente(BigInteger id, ClienteDTO novosDados) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'alterarCliente'");
    }

    public String deletePorId(BigInteger id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletePorId'");
    }  

         //     private void validar (ClienteDTO dto) throws Exception {

        // if(dto.getNomeRazaoSocial().length() < 3 || dto.getNomeRazaoSocial().length() > 50)
        //     throw new NomeEstadoInvalidoException(dto.getNome());
        
        // if(repositorio.existsByNome(dto.getNome()))   
        //     throw new EstadoDuplicadoException(dto.getNome());

//     }

//     public EstadoDTO criarEstado(EstadoDTO entityDTO) throws Exception {  
        
//         validar(entityDTO);  
//         try{    
//             return toDTO(repositorio.save(toEntity(entityDTO)));
//         }catch(Exception e){
//             throw new Exception("Erro ao salvar o estado.");
//         }
//     }

//     public EstadoDTO alterarEstado(BigInteger id, EstadoDTO novosDados) throws Exception {

//         validar(novosDados);
//         if(repositorio.existsByNome(novosDados.getNome()))   
//             throw new EstadoDuplicadoException(novosDados.getNome());
            
//         try{     
//          return toDTO(repositorio.save(toEntity(novosDados)));
//         }catch(Exception e){
//             throw new Exception("Alteração não foi realizada.");
//         }                                   
//     }

//     public String deletePorId(BigInteger id) throws Exception {
//         repositorio.deleteById(id);
//         return "Excluído";
//     }  
    
}



