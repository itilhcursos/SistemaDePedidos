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

import br.com.itilh.bdpedidos.sistemapedidos.dto.MunicipioDTO;
import br.com.itilh.bdpedidos.sistemapedidos.exception.IdInexistenteException;
import br.com.itilh.bdpedidos.sistemapedidos.service.MunicipioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;



@RestController
@SecurityRequirement(name = "bearerAuth")
public class MunicipioController {

    @Autowired
    private MunicipioService service;

   
    @GetMapping("/municipios")
    public Page<MunicipioDTO> getMunicipios(
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return service.listarMunicipios(pageable);
    }

    @GetMapping("/municipios/estado-id/{id}")
    public Page<MunicipioDTO> getMunicipiosPorEstadoId(@PathVariable BigInteger id,
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ){
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return service.listarMunicipiosPorEstadoId(id, pageable);
    }

    @GetMapping("/municipios/estado-nome/{nome}")
    public Page<MunicipioDTO> getMunicipiosPorEstadoNome(@PathVariable String nome,
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ){
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return service.listarMunicipiosPorEstadoNome(nome, pageable);
    }

    @GetMapping("/municipio/{id}")
    public MunicipioDTO getMunicipioPorId(@PathVariable BigInteger id) throws Exception {
        try{
            return service.buscarMunicipioPorId(id);
        }catch(IdInexistenteException e){
            //tratar a exceção de forma personalizada
            throw e;
        }
        
    }

    @PostMapping("/municipio")
    public MunicipioDTO postMunicipio(@RequestBody MunicipioDTO origem) throws Exception {    
        return service.criarMunicipio(origem);
    }
    
    @PutMapping("/municipio/{id}")
    public MunicipioDTO putMunicipio(@PathVariable BigInteger id, @RequestBody MunicipioDTO origem) throws Exception {
        return service.alterarMunicipio(id, origem);
    }

    @DeleteMapping("/municipio/{id}")
    public String deleteMunicipio(@PathVariable BigInteger id) throws Exception{
        return service.excluirMunicipio(id);
    }
    
    
}
