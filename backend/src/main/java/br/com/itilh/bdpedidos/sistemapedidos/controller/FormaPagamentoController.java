package br.com.itilh.bdpedidos.sistemapedidos.controller;

<<<<<<< HEAD
import org.springframework.web.bind.annotation.RestController;

import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.util.ModoBusca;

=======
>>>>>>> develop
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
<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
=======
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.util.ModoBusca;
>>>>>>> develop


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

    @GetMapping("/formas-pagamento/descricao/{descricao}")
<<<<<<< HEAD
    public List<FormaPagamento> getFormaPagamentosPorDescricao(@PathVariable String descricao, @RequestParam(required = true) ModoBusca modoBusca) {
=======
    public List<FormaPagamento> getFormaPagamentoPorDescricao(@PathVariable String descricao,
    @RequestParam(required = true) ModoBusca modoBusca) {
>>>>>>> develop
        if(modoBusca.equals(ModoBusca.EXATO)){
            return repositorio.findByDescricao(descricao);
        }else if (modoBusca.equals(ModoBusca.INICIADO)){
            return repositorio.findByDescricaoStartingWithIgnoreCase(descricao);
        }else if (modoBusca.equals(ModoBusca.FINALIZADO)){
<<<<<<< HEAD
            return repositorio.findByDescricaoEndingWithIgnoreCase(descricao);
        }else{
            return repositorio.findByDescricaoContainingIgnoreCase(descricao);
        }
=======
            return repositorio.findByDescricaoStartingWithIgnoreCase(descricao);
        }else{
            return repositorio.findByDescricaoStartingWithIgnoreCase(descricao);
        }       
>>>>>>> develop
    }

    @GetMapping("/forma-pagamento/{id}")
    public FormaPagamento getPorId(@PathVariable BigInteger id) throws Exception {
        return repositorio.findById(id).orElseThrow(
            () -> new Exception("ID inválido.")
         );
<<<<<<< HEAD
    }

    @PostMapping("/forma-pagamento")
    public FormaPagamento criarFormaPagamento(@RequestBody FormaPagamento entity) throws Exception 
    {
=======
    }  

    @PostMapping("/forma-pagamento")
    public FormaPagamento criarFormaPagamento(@RequestBody FormaPagamento entity) throws Exception { 
>>>>>>> develop
        try{               
            if(entity.getId() != null){
                throw new Exception("Entidade já existe.");
            }
            return repositorio.save(entity);
        }catch(Exception e){
<<<<<<< HEAD
            throw new Exception("Erro ao salvar a forma de pagamento.");
        }
    }
    
    @PutMapping("forma-pagamento/{id}")
    public FormaPagamento alterFormaPagamento(@PathVariable BigInteger id, @RequestBody FormaPagamento novosDados) throws Exception {
        
        Optional<FormaPagamento> formapagamentoArmazenado = repositorio.findById(id);
        if (formapagamentoArmazenado.isPresent()) {
            //Atribuir novo nome ao objeto já existente no banco de dados
            formapagamentoArmazenado.get().setDescricao(novosDados.getDescricao());
            formapagamentoArmazenado.get().setAtivo(novosDados.getAtivo());

            return repositorio.save(formapagamentoArmazenado.get());
        }
        throw new Exception("Alteração não foi realizada.");
    }

    @DeleteMapping("/forma-pagamento/{id}") 
    public String deletePorId(@PathVariable BigInteger id) throws Exception {
        Optional<FormaPagamento> formapagamentoArmazenado = repositorio.findById(id);
        if (formapagamentoArmazenado.isPresent()) {
            repositorio.delete(formapagamentoArmazenado.get());
            return "Excluído";
        }
        throw new Exception("Id não econtrado para a exclusão");
    }

}
=======
            throw new Exception("Erro ao salvar o estado.");
        }
    }
    
    @PutMapping("/forma-pagamento/{id}")
    public FormaPagamento alterarFormaPagamento(@PathVariable BigInteger id, 
                                @RequestBody FormaPagamento novosDados) throws Exception {

        Optional<FormaPagamento> formaPagamentoArmazenada = repositorio.findById(id);
        if(formaPagamentoArmazenada.isPresent()){
            formaPagamentoArmazenada.get().setDescricao(novosDados.getDescricao());
            formaPagamentoArmazenada.get().setAtivo(novosDados.getAtivo());
            return repositorio.save(formaPagamentoArmazenada.get());
        }        
        throw new Exception("Alteração não foi realizada.");
    }

    @DeleteMapping("/forma-pagamento/{id}")
    public String deletePorId(@PathVariable BigInteger id) throws Exception {

        Optional<FormaPagamento> formaPagamentoArmazenada = repositorio.findById(id);
        if(formaPagamentoArmazenada.isPresent()){
            repositorio.delete(formaPagamentoArmazenada.get());
            return "Excluído";
        }
        throw new Exception("Id não econtrado para a exclusão");
    }    

    
}
>>>>>>> develop
