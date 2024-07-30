package br.com.itilh.bdpedidos.sistemapedidos.controller;

import java.math.BigInteger;

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

import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;


@RestController
public class FormaPagamentoController {

    private final FormaPagamentoRepository repositorio;

    public FormaPagamentoController(FormaPagamentoRepository repositorio){
        this.repositorio = repositorio;
    }

    @GetMapping("/formaPagamento")
    public Page<FormaPagamento> getTodos(
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);

        return  (Page<FormaPagamento>) repositorio.findAll(pageable);
    }

    @GetMapping("/formaPagamento/{id}")
    public FormaPagamento getPorId(@RequestParam BigInteger id) throws Exception {
        return repositorio.findById(id).orElseThrow(
            () -> new Exception("ID inválido.")
         );
    }

    @PostMapping("/formaPagamento")
    public FormaPagamento postFormaPagamento(@RequestBody FormaPagamento entity) throws Exception {    
        try{ 
            return repositorio.save(entity);
        }catch (Exception ex){
            throw new Exception("Não foi possível criar o município." + ex.getMessage());
        }
    }
    
    @PutMapping("/formaPagamento/{id}")
    public FormaPagamento putMunicipio(@PathVariable BigInteger id, @RequestBody FormaPagamento entity) throws Exception {
        try{ 
            return repositorio.save(entity);
        }catch (Exception ex){
            throw new Exception("Não foi possível alterar o município." + ex.getMessage());
        }
    }

    @DeleteMapping("/formaPagamento/{id}")
    public String deleteFormaPagamento(@PathVariable BigInteger id) throws Exception{
        try{ 
             repositorio.deleteById(id);
             return "Excluído";
        }catch (Exception ex){
            throw new Exception("Não foi possível excluir o id informado." + ex.getMessage());
        }
    }

    
}
