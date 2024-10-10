package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import br.com.itilh.bdpedidos.sistemapedidos.dto.ClienteDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.CleinteDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;


@Service
public class ClienteService extends GenericService<Cliente,ClienteDTO>{

    @Autowired
    private ClienteRepository repositorio;


    public Page<ClienteDTO> listarCliente(Pageable pageable) {
        return toPageDTO(repositorio.findAll(pageable));
    }

    public ClienteDTO buscarClientePorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id)
        .orElseThrow(()-> new IdInexistenteException("Cliente", id)));
    }

    public ClienteDTO criarCliente(ClienteDTO origem) throws Exception {    
        validar(origem);
        return toDTO(repositorio.save(toEntity(origem)));
    }

    public ClienteDTO alterarCliente(BigInteger id, ClienteDTO origem) throws Exception {
        validar(origem);
        return toDTO(repositorio.save(toEntity(origem)));
    }

    private void validar (ClienteDTO dto) throws Exception {

        if(repositorio.existsByNomeRazaoSocial(dto.getNomeRazaoSocial()))   
            throw new CleinteDuplicadoException(dto.getNomeRazaoSocial());

    }

    public String excluirCliente(BigInteger id) throws Exception{
        try{ 
            repositorio.deleteById(id);
             return "Excluído";
        }catch (Exception ex){
            throw new Exception("Não foi possível excluir o id informado." + ex.getMessage());
        }
    }


}
