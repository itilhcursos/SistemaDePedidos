package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ClienteDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.CnpjDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.CpfDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;

@Service
public class ClienteService extends GenericService<Cliente, ClienteDTO> {
    @Autowired

    ClienteRepository repositorio;

    public Page<ClienteDTO> listarCliente(Pageable pageable){
        return toPageDTO(repositorio.findAll(pageable));
    }

    public Page<ClienteDTO> buscar(Pageable pageable, String txtBusca){
        return toPageDTO(repositorio.findByNomeRazaoSocialContainingIgnoreCase(pageable, txtBusca));
    }

    public ClienteDTO getPorId(BigInteger id) throws Exception{
        return toDTO(repositorio.findById(id).orElseThrow(
            ()-> new IdInexistenteException("Cliente", id)));
    }

    // public Page<ClienteDTO> listarClientesPorMunicipioId(BigInteger id, Pageable pageable) {
    //     return toPageDTO(repositorio.findByClienteId(id, pageable));
    // }

    // public Page<ClienteDTO> listarClientesPorMunicipioNome(String nome, Pageable pageable) {
    //     return toPageDTO(repositorio.findByClienteNomeIgnoreCase(nome, pageable));
    // }

     private void validar(ClienteDTO origem) {
        
        if( origem.getCpf() != null &&  origem.getCpf() != "" && repositorio.existsByCpf(origem.getCpf())){
            if(origem.getId() == null){    
                throw new CpfDuplicadoException(origem.getCpf());
            }else{
                Cliente c = repositorio.getReferenceById(origem.getId());
                if (!c.getCpf().equals(origem.getCpf())) {
                    throw new CpfDuplicadoException(origem.getCpf());
                }
            }
           
        }
         
        if(origem.getCnpj() != null && origem.getCnpj() != "" && repositorio.existsByCnpj(origem.getCnpj())){ //isEmpty verificar se uma String está vazia  utilizando o método isEmpty().
            if(origem.getId() == null){
                throw new CnpjDuplicadoException(origem.getCnpj());
            }else{
                Cliente c = repositorio.getReferenceById(origem.getId());
                if(!c.getCnpj().equals(origem.getCnpj())){
                    throw new CnpjDuplicadoException(origem.getCnpj());
                }
            }
            
        }
          
          
    }

    public ClienteDTO criarCliente(ClienteDTO origem) throws Exception{
        validar(origem);
        return toDTO(repositorio.save(toEntity(origem)));
    }
   
    public ClienteDTO alterarCliente(BigInteger id, ClienteDTO origem) throws Exception {
        validar(origem);
        try{     
         return toDTO(repositorio.save(toEntity(origem)));
        }catch(Exception e){
            throw new Exception("Alteração não foi realizada.");
        }                                   
    }

    public String deletePorId(BigInteger id) throws Exception {
        repositorio.deleteById(id);
        return "Excluído";
    }

    
}
