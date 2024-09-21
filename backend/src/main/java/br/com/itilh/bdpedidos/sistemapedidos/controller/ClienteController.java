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

import br.com.itilh.bdpedidos.sistemapedidos.service.ClienteService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
@RestController
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {

    @Autowired
    ClienteService clienteService;



    @GetMapping("/Clientes")
    public Page<ClienteDTO> BuscarClientes(
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return clienteService.listarClientes(pageable);
    }

     @GetMapping("/Clientes/municipio-id/{id}")
    public Page<ClienteDTO> BuscarClientePorMunicipioId(@PathVariable BigInteger id,
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ){
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return clienteService.listarClientePorMunicipioId(id, pageable);
    }
    

    @GetMapping("/Clientes/municipio-nome/{nome}")
    public Page<ClienteDTO> BuscarClientePorMunicipioNome(@PathVariable String nome,
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ){
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return clienteService.listarClientePorMunicipioNome(nome, pageable);
    }

 @GetMapping("/Cliente/{id}")
    public ClienteDTO BuscarClientePorId(@PathVariable BigInteger id) throws Exception {
        return clienteService.buscarClientePorId(id);
    }    


    @PostMapping("/Cliente")
    public ClienteDTO criarItemPedido(@RequestBody ClienteDTO entity) throws Exception {
        return clienteService.criarCliente(entity);
    }
    


    @PutMapping("/Cliente/{id}")
    public ClienteDTO alterarCliente(@PathVariable BigInteger id, @RequestBody ClienteDTO origem) throws Exception {
        return clienteService.alterarCliente(id,origem);
    }



    @DeleteMapping("/Cliente/{id}")
    public String deleteCliente(@PathVariable BigInteger id) throws Exception {
        return clienteService.excluirCliente(id);
    }

}
