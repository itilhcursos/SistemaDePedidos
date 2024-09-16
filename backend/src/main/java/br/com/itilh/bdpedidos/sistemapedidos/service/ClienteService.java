package br.com.itilh.bdpedidos.sistemapedidos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ClienteDTO;
import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.repository.ClienteRepository;

@Service
public class ClienteService extends GenericService<Cliente,ClienteDTO> {

    @Autowired
    ClienteRepository repositorio;

    public Page<ClienteDTO> getTodos(Pageable pageable){
        return toPageDTO(repositorio.findAll(pageable));
    }

    
}
