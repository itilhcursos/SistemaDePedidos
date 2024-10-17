package br.com.itilh.bdpedidos.sistemapedidos.service;

import java.math.BigInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ClienteDTO;
import br.com.itilh.bdpedidos.sistemapedidos.dto.EstadoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.ClienteDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.EstadoDuplicadoException;
import br.com.itilh.bdpedidos.sistemapedidos.exception.NomeEstadoInvalidoException;
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;

@Service
public class ClienteService extends GenericService<Cliente, ClienteDTO>{

    @Autowired
    ClienteRepository repositorio;

    public Page<ClienteDTO> getTodos(Pageable pageable){
        return toPageDTO(repositorio.findAll(pageable));
    }

    private Page<ClienteDTO> toPageDTO(Page<Cliente> all) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toPageDTO'");
    }

    public ClienteDTO getporId(BigInteger id) throws Exception {
        return toPageDTO(repositorio.findById(id).orElseThrow(
            () -> new Exception("ID Inválido")));
    }

    private void validar (ClienteDTO dto) throws Exception {

        if(dto.getNomeRazaoSocial().length() < 3 || dto.getNomeRazaoSocial().length() > 50)
            throw new NomeEstadoInvalidoException(dto.getNomeRazaoSocial());
        
        if(repositorio.existsByNomeRazaoSocial(dto.getNomeRazaoSocial()))
            throw new EstadoDuplicadoException(dto.getNomeRazaoSocial());

    }

    public ClienteDTO criarCliente(ClienteDTO entityDTO) throws Exception {
        
        validar(entityDTO);
        try{
            return toDTO(repositorio.saveAll((S) toEntity(entityDTO)));
        }catch(Exception e){
            throw new Exception("Erro ao salvar o estado.");
        }
    }

    private Object toEntity(ClienteDTO entityDTO) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'toEntity'");
    }

    public ClienteDTO alterarCliente(BigInteger id, ClienteDTO novosDados) throws Exception {

        validar(novosDados);
        if(repositorio.existsByNomeRazaoSocial(novosDados.getNomeRazaoSocial()))
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

    public String apagar(BigInteger id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'apagar'");
    }


}