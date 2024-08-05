package br.com.itilh.bdpedidos.sistemapedidos.controller;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

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
import br.com.itilh.bdpedidos.sistemapedidos.util.ModoBusca;
@RestController
public class FormaPagamentoController {
 
    private final FormaPagamentoRepository repositorio;

    public FormaPagamentoController(FormaPagamentoRepository repositorio){
        this.repositorio = repositorio;
    }

    @GetMapping("/formas-pagamento")
    public Page<FormaPagamento> getTodos(
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);

        return  (Page<FormaPagamento>) repositorio.findAll(pageable);
    }

    @GetMapping("/formas-pagamento/nome/{nome}")
    public List<FormaPagamento> getFormaPagamentoPorDescricao(@PathVariable String descricao,
    @RequestParam(required = true) ModoBusca modoBusca) {
        if(modoBusca.equals(ModoBusca.EXATO)){
            return repositorio.findByDescricao(descricao);
        }else if (modoBusca.equals(ModoBusca.INICIADO)){
            return repositorio.findByDescricaoStartingWithIgnoreCase(descricao);
        }else if (modoBusca.equals(ModoBusca.FINALIZADO)){
            return repositorio.findByDescricaoEndingWithIgnoreCase(descricao);
        }else{
            return repositorio.findByDescricaoContainingIgnoreCase(descricao);
        }       
    }
    
    @GetMapping("/formas-pagamento/{id}")
    public FormaPagamento getPorId(@PathVariable BigInteger id) throws Exception {
        return repositorio.findById(id).orElseThrow(
            () -> new Exception("ID inválido.")
         );
    }    

    @PostMapping("/forma-pagamento")
    public FormaPagamento criarFormaPagamento(@RequestBody FormaPagamento entity) throws Exception { 
        try{               
            if(entity.getId() != null){
                throw new Exception("Entidade já existe.");
            }
            return repositorio.save(entity);
        }catch(Exception e){
            throw new Exception("Erro ao salvar a forma de pagamento.");
        }
    }

    @PutMapping("/forma-pagamento/{id}")
    public FormaPagamento alterarFormaPagamento(@PathVariable BigInteger id, 
                                @RequestBody FormaPagamento novosDados) throws Exception {

        Optional<FormaPagamento> formaPagamentoArmazenado = repositorio.findById(id);
        if(formaPagamentoArmazenado.isPresent()){
            //Atribuir novo nome ao objeto já existem no banco de dados
            formaPagamentoArmazenado.get().setDescricao(novosDados.getDescricao());
            formaPagamentoArmazenado.get().setAtivo(novosDados.getAtivo());
            return repositorio.save(formaPagamentoArmazenado.get());
        }        
        throw new Exception("Alteração não foi realizada.");
    }

    @DeleteMapping("/forma-pagamento/{id}")
    public String deletePorId(@PathVariable BigInteger id) throws Exception {

        Optional<FormaPagamento> formaPagamentoArmazenado = repositorio.findById(id);
        if(formaPagamentoArmazenado.isPresent()){
            repositorio.delete(formaPagamentoArmazenado.get());
            return "Excluído";
        }
        throw new Exception("Id não econtrado para a exclusão");
    }    

}