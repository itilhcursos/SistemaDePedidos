package br.com.itilh.bdpedidos.sistemapedidos.controller;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.itilh.bdpedidos.sistemapedidos.model.Municipio;
import br.com.itilh.bdpedidos.sistemapedidos.service.MunicipioService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
public class MunicipioController {
    @Autowired
    private MunicipioService service;

    @GetMapping("/municipios")
    public List<Municipio> getMunicipios() {
        return service.listarMunicipios();
    }

    @GetMapping("/municipios/estado-id/{id}")
    public List<Municipio> getMunicipiosPorEstadoId(@PathVariable BigInteger id) {
        return service.listarMunicipiosPorEstadoId(id);
    }

    @GetMapping("/municipios/estado-nome/{nome}")
    public List<Municipio> getMunicipiosPorEstadoNome(@PathVariable String nome) {
        return service.listarMunicipiosPorEstadoNome(nome);
    }

    @GetMapping("/municipio/{id}")
    public Municipio getMunicipioPorId(@PathVariable BigInteger id) throws Exception {
        return service.buscarMunicipioPorId(id);
    }

    @PostMapping("/municipio")
    public Municipio postMunicipio(@RequestBody Municipio entity) throws Exception {

        return service.criarMunicipio(entity);
    }

    @PutMapping("/municipio/{id}")
    public Municipio putMunicipio(@PathVariable BigInteger id, @RequestBody Municipio entity) throws Exception {
        return service.alterarMunicipio(id, entity);
    }

    @DeleteMapping("/municipio/{id}")
    public String deleteMunicipio(@PathVariable BigInteger id) throws Exception {
        return service.excluirMunicipio(id);
    }

}
