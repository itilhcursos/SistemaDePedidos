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
    private ClienteRepository repository;

    public Page<ClienteDTO> listarClientes(Pageable pageable) {
        return toPageDTO(repository.findAll(pageable));
    }

    public Page<ClienteDTO> listarClientesPorMunicipioId(BigInteger id, Pageable pageable) {
        return toPageDTO(repository.findByMunicipioId(id, pageable));
    }  
    
    public Page<ClienteDTO> listarClientesPorMunicipioNome(String nome, Pageable pageable) {
        return toPageDTO(repository.findByMunicipioNomeIgnoreCase(nome, pageable));
    }    

    public ClienteDTO buscarClientePorId(BigInteger id) throws Exception {
        return toDTO(repository.findById(id)
        .orElseThrow(()-> new IdInexistenteException("Cliente", id)));
    }  

    private void validar(ClienteDTO origem) {
        if(repository.existsByCnpjAndMunicipioId(origem.getCnpj(), origem.getMunicipioId()) ||
           repository.existsByCpfAndMunicipioId(origem.getCpf(), origem.getMunicipioId()))
          throw new ClienteDuplicadoException(origem.getNomeRazaoSocial());
    }    
    
    public ClienteDTO criarCliente(ClienteDTO origem) throws Exception {    
        validar(origem);
        return toDTO(repository.save(toEntity(origem)));
    }  
    
    public ClienteDTO alterarCliente(BigInteger id, ClienteDTO origem) throws Exception {
        validar(origem);
        return toDTO(repository.save(toEntity(origem)));
    }    

    public String excluirCliente(BigInteger id) throws Exception{
    try{ 
        repository.deleteById(id);
         return "Cliente excluído com sucesso";
    }catch (Exception ex){
        throw new Exception("Não foi possível excluir o id informado." + ex.getMessage());
    }
}    


}
