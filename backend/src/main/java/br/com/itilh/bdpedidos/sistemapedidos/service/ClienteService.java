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
        
            private Page<ClienteDTO> toPageDTO(Page<Cliente> all) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'toPageDTO'");
            }
        
            public Page<ClienteDTO> buscar (Pageable pageable, String txtBusca ){
        return toPageDTO(repositorio.findByNomeRazaoSocialContainingIgnoreCase(pageable , txtBusca));
    }

    public ClienteDTO getPorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id).orElseThrow(
                    () -> new Exception("ID inválido.")));
            }  
        
            private ClienteDTO toDTO(Cliente orElseThrow) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'toDTO'");
            }
        
            private void validar (ClienteDTO dto) throws Exception {
        
        if (repositorio.existsByNomeRazaoSocial(dto.getNomeRazaoSocial())){
            if(dto.getId() == null){
                throw new ClienteDuplicadoException(dto.getNomeRazaoSocial());
            }else{
                Cliente c = repositorio.getReferenceById(dto.getId());
                if(!c.getNomeRazaoSocial().equalsIgnoreCase(dto.getNomeRazaoSocial())){
                    throw new ClienteDuplicadoException(dto.getNomeRazaoSocial());
                }
            }            
        }

    }

    public ClienteDTO criarCliente(ClienteDTO entityDTO) throws Exception {  
        
        validar(entityDTO);  
        try{    
            return toDTO(repositorio.saveall(toEntity(entityDTO)));
                    }catch(Exception e){
                        throw new Exception("Erro ao salvar o cliente.");
                    }
                }
            
                private Object toEntity(ClienteDTO entityDTO) {
                    // TODO Auto-generated method stub
                    throw new UnsupportedOperationException("Unimplemented method 'toEntity'");
                }
            
                public ClienteDTO alterarCliente(BigInteger id, ClienteDTO novosDados) throws Exception {

        validar(novosDados);
        return toDTO(repositorio.saveall(toEntity(novosDados)));                           
    }

    public String deletePorId(BigInteger id) throws Exception {
        repositorio.deleteById(id);
        return "Excluído";
    }  
    
}