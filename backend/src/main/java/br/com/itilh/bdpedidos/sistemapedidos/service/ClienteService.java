package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ClienteDTO;
import br.com.itilh.bdpedidos.sistemapedidos.dto.MunicipioDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.CnpjDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.CpfDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.MunicipioDuplicadoException;
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
        // se já existe municipio com mesmo nome e no mesmo estado
        if(repositorio.existsByCpf(origem.getCpf()))
          throw new CpfDuplicadoException(origem.getCpf());

        if(repositorio.existsByCnpj(origem.getCnpj()))
          throw new CnpjDuplicadoException(origem.getCnpj());
          
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
