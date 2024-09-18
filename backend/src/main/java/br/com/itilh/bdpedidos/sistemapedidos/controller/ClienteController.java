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

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import br.com.itilh.bdpedidos.sistemapedidos.dto.ClienteDTO;

//import br.com.itilh.bdpedidos.sistemapedidos.model.Cliente;
import br.com.itilh.bdpedidos.sistemapedidos.service.ClienteService;
//import br.com.itilh.bdpedidos.sistemapedidos.util.ModoBusca;


@RestController
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {


    @Autowired
    ClienteService clienteService;

    @GetMapping("/clientes")
    public Page<ClienteDTO> getTodos(
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);

        return clienteService.getTodos(pageable);
    }

    //  @GetMapping("/clientes/nome/{nome}")
    //  public List<Cliente> getClientesPorNome(@PathVariable String nome,
    //      @RequestParam(required = true) ModoBusca modoBusca) {
    //     if(modoBusca.equals(ModoBusca.EXATO)){
    //      return repositorio.findByNome(nome);
    //     }else if (modoBusca.equals(ModoBusca.INICIADO)){
    //      return repositorio.findByNomeStartingWithIgnoreCase(nome);
    //     }else if (modoBusca.equals(ModoBusca.FINALIZADO)){
    //      return repositorio.findByNomeEndingWithIgnoreCase(nome);
    //      }else{
    //         return repositorio.findByNomeContainingIgnoreCase(nome);
    //     }       
    //  }
        
    @GetMapping("/cliente/{id}")
    public ClienteDTO getPorId(@PathVariable BigInteger id) throws Exception {
        return clienteService.getPorId(id);
    }    

    @PostMapping("/cliente")
    public ClienteDTO criarCliente(@RequestBody ClienteDTO entityDTO) throws Exception { 
       return clienteService.criarCliente(entityDTO);
    }

    @PutMapping("/cliente/{id}")
    public ClienteDTO alterarCliente(@PathVariable BigInteger id, 
        @RequestBody ClienteDTO novosDados) throws Exception {

        return clienteService.alterarCliente(id, novosDados);
    }

    @DeleteMapping("/cliente/{id}")
    public String deletePorId(@PathVariable BigInteger id) throws Exception {
        return clienteService.deletePorId(id);
    }    

}
