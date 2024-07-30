package br.com.itilh.bdpedidos.sistemapedidos.controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;
import br.com.itilh.bdpedidos.sistemapedidos.util.ModoBusca;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;






@RestController
public class FormaPagamentoController {

    private final FormaPagamentoRepository repositorio;

    public FormaPagamentoController(FormaPagamentoRepository repositorio){
        this.repositorio = repositorio;
    }

    @GetMapping("/forma-pagamento")
    public Page<FormaPagamento> getTodos(
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);

        return  (Page<FormaPagamento>) repositorio.findAll(pageable);
    }

    @GetMapping("/forma-pagamento/nome/{nome}")
    public List<FormaPagamento> getFormasPagamentoPorNome(@PathVariable String nome,
    @RequestParam(required = true) ModoBusca modoBusca) {
        if(modoBusca.equals(ModoBusca.EXATO)){
            return repositorio.findByNome(nome);
        }else if (modoBusca.equals(ModoBusca.INICIADO)){
            return repositorio.findByNomeStartingWithIgnoreCase(nome);
        }else if (modoBusca.equals(ModoBusca.FINALIZADO)){
            return repositorio.findByNomeEndinWithIgnoreCase(nome);
        }else{
            return repositorio.findByNomeContainingIgnoreCase(nome);
        }       
    }
    
    
    @GetMapping("/forma-pagamento/{id}")
    public FormaPagamento getPorId(@PathVariable BigInteger id) throws Exception {
        return repositorio.findById(id).orElseThrow(
            () -> new Exception("ID inválido.")
         );
    }    


    @PostMapping("/formas-pagamento")
    public FormaPagamento criarFormaPagamento(@RequestBody FormaPagamento entity) throws Exception { 
        try{               
            if(entity.getId() != null){
                throw new Exception("Entidade já existe.");
            }
            return repositorio.save(entity);
        }catch(Exception e){
            throw new Exception("Erro ao salvar o FormaPagamento.");
        }
    }

    @PutMapping("/forma-pagamento/{id}")
    public FormaPagamento alterarFormaPagamento(@PathVariable BigInteger id, 
                                @RequestBody FormaPagamento novosDados) throws Exception {

        Optional<FormaPagamento> FormapagamentoAmazenado = repositorio.findById(id);
        if(FormapagamentoAmazenado.isPresent()){
           
            FormapagamentoAmazenado.get().setNome(novosDados.getNome());
         
            return repositorio.save(FormapagamentoAmazenado.get());
        }        
        throw new Exception("Alteração não foi realizada.");
    }

    @DeleteMapping("/forma-pagamento/{id}")
    public String deletePorId(@PathVariable BigInteger id) throws Exception {

        Optional<FormaPagamento> FormapagamentoAmazenado = repositorio.findById(id);
        if(FormapagamentoAmazenado.isPresent()){
            repositorio.delete(FormapagamentoAmazenado.get());
            return "Excluído";
        }
        throw new Exception("Id não econtrado para a exclusão");
    }    
}
