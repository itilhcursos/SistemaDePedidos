package br.com.itilh.bdpedidos.sistemapedidos.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ClienteDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.service.ClienteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {

    @Autowired
    private ClienteService service;

    // Endpoint para listar clientes com paginação
    @GetMapping("/clientes")
    public Page<ClienteDTO> getClientes(
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return service.listarClientes(pageable);
    }

    @GetMapping("/clientes/{txtBusca}")
    public Page<ClienteDTO> getBuscarClientes(
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property,
        @PathVariable String txtBusca
    ) {
        Pageable pageable = PageRequest.of(pageNumber-1, pageSize, Sort.Direction.valueOf(direction), property);
        return service.buscar(pageable, txtBusca);
    }

    // Endpoint para buscar cliente por ID
    @GetMapping("/clientes/id/{id}")
    public ClienteDTO getClientePorId(@PathVariable BigInteger id) throws Exception {
        try {
            return service.buscarClientePorId(id);
        } catch (IdInexistenteException e) {
            throw e; // Exceção personalizada
        }
    }

    // Endpoint para criar cliente (POST)
    @PostMapping("/cliente")
    public ClienteDTO postCliente(@RequestBody ClienteDTO origem) throws Exception {
        // Criação de cliente com municipioId e municipioNome
        return service.criarCliente(origem);
    }

    // Endpoint para atualizar cliente (PUT)
    @PutMapping("/cliente/{id}")
    public ClienteDTO putCliente(@PathVariable BigInteger id, @RequestBody ClienteDTO origem) throws Exception {
        // Atualização de cliente com municipioId e municipioNome
        return service.alterarCliente(id, origem);
    }

    // Endpoint para deletar cliente por ID
    @DeleteMapping("/cliente/{id}")
    public String deleteCliente(@PathVariable BigInteger id) throws Exception {
        return service.excluirCliente(id);
    }
}
//Necessito criar os get do municipioNome e municipioId