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
public class ClienteService extends GenericService<Cliente,ClienteDTO> {

    @Autowired
    ClienteRepository repositorio;

    public Page<ClienteDTO> getTodos(Pageable pageable){
        return toPageDTO(repositorio.findAll(pageable));
    }

    public ClienteDTO getPorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id)
        
        .orElseThrow(() -> new Exception("ID Inválido!")));
    }

    private void validar(ClienteDTO dto) throws Exception{
        // Verifica duplicações.
        if (repositorio.existsByCpf(dto.getCpf()) || repositorio.existsByCnpj(dto.getCnpj()) || repositorio.existsByEmail(dto.getEmail())){
            if(dto.getId() == null){

                // Verifica se o usuario Cadastrou um CPF E um CNPJ, para garantir que só seja cadastrado ou um ou outro.
                if (dto.getCpf() != "" && dto.getCnpj() != ""){
                    throw new Exception ("Cadastre ou um CPF ou um CNPJ.");
                }
                if (repositorio.existsByCpf(dto.getCpf()))
                    throw new ClienteDuplicadoException("CPF");

                if (repositorio.existsByCnpj(dto.getCnpj()))
                    throw new ClienteDuplicadoException("CNPJ");

                if (repositorio.existsByEmail(dto.getEmail()))
                    throw new ClienteDuplicadoException("E-mail");
            } /* else {
                Cliente c = repositorio.getReferenceById(dto.getId());
                if(!c.getCpf().equalsIgnoreCase(dto.getCpf()) || !c.getCnpj().equalsIgnoreCase(dto.getCnpj()) || !c.getEmail().equalsIgnoreCase(dto.getEmail())){
                    throw new Exception("Não altere CPF, CNPJ ou Email.");
                }
            } */
        }
    }

    public ClienteDTO criar(ClienteDTO dto) throws Exception {
        validar(dto);
        return toDTO(repositorio.save(toEntity(dto)));
    }

    public ClienteDTO alterar(BigInteger id, ClienteDTO dto) throws Exception {
        validar(dto);
        return toDTO(repositorio.save(toEntity(dto)));
        
    }

    public String apagar(BigInteger id) throws Exception {
        repositorio.deleteById(id);
        return ("Cliente apagado!");
    }
}
