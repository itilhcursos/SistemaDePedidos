package br.com.itilh.bdpedidos.sistemapedidos.controller;

<<<<<<< HEAD
import java.math.BigInteger;

=======
>>>>>>> developer
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
=======
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
>>>>>>> developer
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ClienteDTO;
<<<<<<< HEAD
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.service.ClienteService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
=======
import br.com.itilh.bdpedidos.sistemapedidos.service.ClienteService;
>>>>>>> developer
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {

    @Autowired
<<<<<<< HEAD
    private ClienteService service;

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

    @GetMapping("/clientes/municipio-id/{id}")
    public Page<ClienteDTO> getClientesPorMunicipioId(@PathVariable BigInteger id,
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return service.listarClientesPorMunicipioId(id, pageable);
    }

    @GetMapping("/clientes/municipio-nome/{nome}")
    public Page<ClienteDTO> getClientesPorMunicipioNome(@PathVariable String nome,
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return service.listarClientesPorMunicipioNome(nome, pageable);
    }

    @GetMapping("/cliente/{id}")
    public ClienteDTO getClientePorId(@PathVariable BigInteger id) throws Exception {
        try {
            return service.buscarClientePorId(id);
        } catch (IdInexistenteException e) {
            // Tratar exceção de forma personalizada
            throw e;
        }
    }

    @PostMapping("/cliente")
    public ClienteDTO postCliente(@RequestBody ClienteDTO origem) throws Exception {
        return service.criarCliente(origem);
    }

    @PutMapping("/cliente/{id}")
    public ClienteDTO putCliente(@PathVariable BigInteger id, @RequestBody ClienteDTO origem) throws Exception {
        return service.alterarCliente(id, origem);
    }

    @DeleteMapping("/cliente/{id}")
    public String deleteCliente(@PathVariable BigInteger id) throws Exception {
        return service.excluirCliente(id);
    }
=======
    ClienteService service;

        @GetMapping("/clientes/{txtBusca}")
    public Page<ClienteDTO> getPorNome(
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property,
        @PathVariable String txtBusca
    ) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);

        return service.buscar(pageable, txtBusca);
    }

>>>>>>> developer
}
