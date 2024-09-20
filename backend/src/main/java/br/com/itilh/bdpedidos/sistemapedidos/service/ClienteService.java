package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ClienteDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
/* import br.com.itilh.bdpedidos.sistemapedidos.exception.ClienteDuplicadoException; */
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;

@Service
public class ClienteService extends GenericService<Cliente, ClienteDTO> {

    @Autowired
    ClienteRepository repository;

    public Page<ClienteDTO> listarClientes(Pageable pageable) {
        return toPageDTO(repository.findAll(pageable));
    }

    /* public Page<MunicipioDTO> listarMunicipiosPorEstadoId(BigInteger id, Pageable pageable) {
        return toPageDTO(repository.findByEstadoId(id, pageable));
    } */ 

    private void validar (ClienteDTO dto) throws Exception {

        if(dto.getNomeRazaoSocial().length() < 3 || dto.getNomeRazaoSocial().length() > 50)
            throw new NomeRazaoSocialClienteInvalidoException(dto.getNomeRazaoSocial());
        
        if(repository.existsByNomeRazaoSocial(dto.getNomeRazaoSocial()))   
            throw new ClienteDuplicadoException(dto.getNomeRazaoSocial());
    }

    public ClienteDTO criarCliente(ClienteDTO entityDTO) throws Exception {  
        
        validar(entityDTO);  
        try{    
            return toDTO(repository.save(toEntity(entityDTO)));
        }catch(Exception e){
            throw new Exception("Erro ao salvar o cliente.");
        }
    }

    public ClienteDTO alterarCliente(BigInteger id, ClienteDTO novosDados) throws Exception {

        validar(novosDados);
        if(repository.existsByNomeRazaoSocial(novosDados.getNomeRazaoSocial()))   
            throw new ClienteDuplicadoException(novosDados.getNomeRazaoSocial());
            
        try{     
         return toDTO(repository.save(toEntity(novosDados)));
        }catch(Exception e){
            throw new Exception("Alteração não foi realizada.");
        }                                   
    }

    public String deletePorId(BigInteger id) throws Exception {
        repository.deleteById(id);
        return "Excluído";
    } 
}