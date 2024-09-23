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

    public Page<ClienteDTO> getTodos(Pageable pageable) {
        return toPageDTO(repositorio.findAll(pageable));
    }

    public ClienteDTO getPorId(BigInteger id) throws Exception {
        return toDTO(repositorio.findById(id)

                .orElseThrow(() -> new Exception("ID Inválido!")));
    }

    public ClienteDTO criar(ClienteDTO dto) throws Exception {
        try {
            return toDTO(repositorio.save(toEntity(dto)));

        } catch (Exception e) {
            throw new Exception("Erro ao salvar o Cliente");
        }
    }

    public ClienteDTO alterar(BigInteger id, ClienteDTO novosDados) throws Exception {
        try {
            return toDTO(repositorio.save(toEntity(novosDados)));
        } catch (Exception e) {
            throw new Exception("Erro ao alterar o Cliente");
        }
    }

    public String apagar(BigInteger id) throws Exception {
        repositorio.deleteById(id);
        return ("Cliente apagado!");
    }
}
