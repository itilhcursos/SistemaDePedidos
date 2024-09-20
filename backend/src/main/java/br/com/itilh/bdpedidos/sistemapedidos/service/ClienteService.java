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
public class ClienteService extends GenericService<Cliente, ClienteDTO>{

    @Autowired
    ClienteRepository repositorio;

    public Page<ClienteDTO> getTodos(Pageable pageable){
        return toPageDTO(repositorio.findAll(pageable));
    }

    public ClienteDTO getporId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id).orElseThrow(
            () -> new Exception("ID Inválido")));
    }

    private void validar(ClienteDTO origem) {
        // se já existe cliente com mesmo nome e no mesmo municipio
        if(repositorio.existsByNomeRazaoSocialAndMunicipioId(origem.getNomeRazaoSocial(), origem.getMunicipioId()))
          throw new ClienteDuplicadoException(origem.getNomeRazaoSocial());
    }

    public ClienteDTO criarCliente(ClienteDTO entityDTO) throws Exception {  
        
        validar(entityDTO);  
        try{    
            return toDTO(repositorio.save(toEntity(entityDTO)));
        }catch(Exception e){
            throw new Exception("Erro ao salvar o estado.");
        }
    }

    public ClienteDTO alterarCliente(BigInteger id, ClienteDTO novosDados) throws Exception {

        validar(novosDados);
        if(repositorio.existsByNomeRazaoSocialAndMunicipioId(novosDados.getNomeRazaoSocial(), novosDados.getMunicipioId()))   
            throw new ClienteDuplicadoException(novosDados.getNomeRazaoSocial());

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
