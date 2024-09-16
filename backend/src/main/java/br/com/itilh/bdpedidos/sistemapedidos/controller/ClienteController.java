package br.com.itilh.bdpedidos.sistemapedidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
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
    
}
