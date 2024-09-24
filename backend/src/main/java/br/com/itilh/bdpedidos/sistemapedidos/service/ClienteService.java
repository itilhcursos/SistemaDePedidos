package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ClienteDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ClienteDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;
@Service
public class ClienteService extends GenericService<Cliente, ClienteDTO> {


    @Autowired
    private ClienteRepository clienteRepository;

    public Page<ClienteDTO> listarClientes(Pageable pageable){
     return toPageDTO(clienteRepository.findAll(pageable));
    }

    private void validar(ClienteDTO origem){
        if(clienteRepository.existsByNomeRazaoSocial(origem.getNomeRazaoSocial()))
        throw new ClienteDuplicadoException(origem.getNomeRazaoSocial());

    }

    
 public Page<ClienteDTO> listarClientePorMunicipioId(BigInteger id, Pageable pageable) {
        return toPageDTO(clienteRepository.findBymunicipioId(id, pageable));
    }


    public Page<ClienteDTO> listarClientePorMunicipioNome(String nome, Pageable pageable) {
        return toPageDTO(clienteRepository.findBymunicipioNomeIgnoreCase(nome, pageable));
    }

    public ClienteDTO buscarClientePorId(BigInteger id) throws Exception {
        return toDTO(clienteRepository.findById(id)
        .orElseThrow(()-> new IdInexistenteException("Cliente", id)));
    }

    public ClienteDTO criarCliente(ClienteDTO origem) throws Exception {    
       validar(origem);
        return toDTO(clienteRepository.save(toEntity(origem)));
    }
public ClienteDTO alterarCliente (BigInteger id, ClienteDTO origem) throws Exception {
     validar(origem);
    return toDTO(clienteRepository.save(toEntity(origem)));
    }

    public String excluirCliente(BigInteger id) throws Exception{
        try{ 
           clienteRepository.deleteById(id);
             return "Excluído com sucesso";
        }catch (Exception ex){
            throw new Exception("Não foi possível excluir o id informado." + ex.getMessage());
        }
    }
}
