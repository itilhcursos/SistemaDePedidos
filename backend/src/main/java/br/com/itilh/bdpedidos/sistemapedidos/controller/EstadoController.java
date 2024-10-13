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

import br.com.itilh.bdpedidos.sistemapedidos.dto.EstadoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.dto.MunicipioDTO;
import br.com.itilh.bdpedidos.sistemapedidos.service.EstadoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class EstadoController {

    @Autowired
    EstadoService estadoService;

    @GetMapping("/estados")
    public Page<EstadoDTO> getTodos(
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);

        return estadoService.getTodos(pageable);
    }

    @GetMapping("/estados/{txtBusca}")
    public Page<EstadoDTO> getBusca(
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property,
        @PathVariable String txtBusca
    ) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return estadoService.buscar(pageable, txtBusca);
    }
        
    @GetMapping("/estado/{id}")
    public EstadoDTO getPorId(@PathVariable BigInteger id) throws Exception {
        return estadoService.getPorId(id);
    }    

    @PostMapping("/estado")
    public EstadoDTO criarEstado(@RequestBody EstadoDTO entityDTO) throws Exception { 
       return estadoService.criarEstado(entityDTO);
    }

    @PutMapping("/estado/{id}")
    public EstadoDTO alterarEstado(@PathVariable BigInteger id, 
                                @RequestBody EstadoDTO novosDados) throws Exception {

        return estadoService.alterarEstado(id, novosDados);
    }

    @DeleteMapping("/estado/{id}")
    public String deletePorId(@PathVariable BigInteger id) throws Exception {
        return estadoService.deletePorId(id);
    }    
}
