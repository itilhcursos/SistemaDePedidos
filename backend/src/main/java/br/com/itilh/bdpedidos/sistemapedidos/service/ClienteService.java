package br.com.itilh.bdpedidos.sistemapedidos.service;


import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ClienteDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ClienteDuplicadoException;
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

   

         private void validar (ClienteDTO dto) throws Exception {

        
         if(repositorio.existsByNomeRazaoSocial(dto.getNomeRazaoSocial()))   
             throw new ClienteDuplicadoException(dto.getNomeRazaoSocial());

     }

     public ClienteDTO criarCliente(ClienteDTO entityDTO) throws Exception {  
         
         try{    
             return toDTO(repositorio.save(toEntity(entityDTO)));
         }catch(Exception e){
             throw new Exception("Erro ao salvar cliente.");
         }
     }

     public ClienteDTO alterarCliente(BigInteger id, ClienteDTO novosDados) throws Exception {

         validar(novosDados);
         if(repositorio.existsByNomeRazaoSocial(novosDados.getNomeRazaoSocial()))   
             throw new ClienteDuplicadoException(novosDados.getNomeRazaoSocial());
            
         try{     
          return toDTO(repositorio.save(toEntity(novosDados)));
         }catch(Exception e){
            throw new Exception("Alteração não realizada.");
         }                                   
    }

     public String deletePorId(BigInteger id) throws Exception {
         repositorio.deleteById(id);
         return "Excluído";
     }  
    
}



