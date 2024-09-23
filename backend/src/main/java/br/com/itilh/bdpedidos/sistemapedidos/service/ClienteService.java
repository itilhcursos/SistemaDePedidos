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
public class ClienteService extends GenericService<Cliente, ClienteDTO> {
    @Autowired

    ClienteRepository repositorio;

    public Page<ClienteDTO> listarCliente(Pageable pageable){
        return toPageDTO(repositorio.findAll(pageable));
    }

    public ClienteDTO getPorId(BigInteger id) throws Exception{
        return toDTO(repositorio.findById(id).orElseThrow(
            ()-> new Exception("ID Invalido.")));
    }

   public ClienteDTO criarCliente(ClienteDTO origem) throws Exception{
        return toDTO(repositorio.save(toEntity(origem)));
   }
   
   public ClienteDTO alterarCliente(BigInteger id, ClienteDTO origem) throws Exception {
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
