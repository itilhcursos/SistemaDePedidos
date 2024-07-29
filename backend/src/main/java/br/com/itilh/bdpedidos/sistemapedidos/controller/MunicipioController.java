package br.com.itilh.bdpedidos.sistemapedidos.controller;

import java.math.BigInteger;
import java.util.List;

import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.itilh.bdpedidos.sistemapedidos.dto.MunicipioDTO;
import br.com.itilh.bdpedidos.sistemapedidos.model.Municipio;
import br.com.itilh.bdpedidos.sistemapedidos.repository.MunicipioRepository;
import br.com.itilh.bdpedidos.sistemapedidos.service.MunicipioService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class MunicipioController {

    @Autowired
    private MunicipioService service;

    @GetMapping("/municipios")
    public Page<MunicipioDTO> getMunicipios(
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property) {

        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return service.getMunicipios(pageable);
    }

    @GetMapping("/municipios/estado-id/{id}")
    public  Page<MunicipioDTO> getMunicipiosPorEstadoId(@PathVariable BigInteger id,
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property) {

            Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);

        return service.getMunicipiosPorEstadoId(id, pageable);
    }

    @GetMapping("/municipios/estado-nome/{nome}")
    public Page<MunicipioDTO> getMunicipiosPorEstadoNome(@PathVariable String nome,
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property) {

            Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);

        return service.getMunicipiosPorEstadoNome(nome, pageable);
    }

    @GetMapping("/municipio/{id}")
    public MunicipioDTO getMunicipioPorId(@PathVariable BigInteger id) throws Exception {
        return service.getMunicipioPorId(id);
    }

    @PostMapping("/municipio")
    public MunicipioDTO postMunicipio(@RequestBody MunicipioDTO dto) throws Exception {
        return service.salvarMunicipio(dto);
    }

    @PutMapping("/municipio/{id}")
    public MunicipioDTO putMunicipio(@PathVariable BigInteger id, @RequestBody MunicipioDTO dto) throws Exception {
        return service.alterarMunicipio(id, dto);
    }

    @DeleteMapping("/municipio/{id}")
    public String deleteMunicipio(@PathVariable BigInteger id) throws Exception {
        return service.apagarMunicipio(id);
    }

}
