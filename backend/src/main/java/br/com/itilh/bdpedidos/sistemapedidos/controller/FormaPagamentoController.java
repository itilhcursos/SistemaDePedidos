package br.com.itilh.bdpedidos.sistemapedidos.controller;

import org.springframework.web.bind.annotation.RestController;

import br.com.itilh.bdpedidos.sistemapedidos.model.FormaPagamento;
import br.com.itilh.bdpedidos.sistemapedidos.repository.FormaPagamentoRepository;

import java.math.BigInteger;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class FormaPagamentoController {

    private final FormaPagamentoRepository repository;

    // Construtor para settar o repository 
    public FormaPagamentoController (FormaPagamentoRepository repository){
        this.repository = repository;
    }

    // Endpoint para obter todos os registros da tabela
    // @GetMapping("/formasPagamento")
    // public List<FormaPagamento> getAll () {
    //     return (List<FormaPagamento>) repository.findAll();
    // }
    @GetMapping("/formasPagamento")
    public Page<FormaPagamento> getTodos(
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);

        return  (Page<FormaPagamento>) repository.findAll(pageable);
    }

    @GetMapping("/formaPagamento/{id}")
    public FormaPagamento getById (@PathVariable BigInteger id) throws Exception {
        return repository.findById(id).orElseThrow(
            () -> new Exception("Id inválido")
        );
    }

    @PostMapping("/formaPagamento")
    public FormaPagamento posFormaPagamento (@RequestBody FormaPagamento entity) throws Exception {
        try {
            return repository.save(entity);
        } catch (Exception e) {
            throw new Exception("Erro ao salvar a forma de pagamento");
        }
    }

    @PutMapping("formaPagamento/{id}")
    public FormaPagamento editFormaPagamento (@PathVariable BigInteger id, @RequestBody FormaPagamento entity) throws Exception {
        Optional<FormaPagamento> atualData = repository.findById(id);
        if (atualData.isPresent()) {
            atualData.get().setDescricao(entity.getDescricao());
            atualData.get().setAtivo(entity.getAtivo());
            return repository.save(atualData.get());
        }
        throw new Exception("Não foi possível alterar a forma de pagamento informado");
    }

    @DeleteMapping("formaPagamento/{id}")
    public String deleteById (@PathVariable BigInteger id) throws Exception {
        try {
            repository.deleteById(id);
            return "Excluído";
        } catch (Exception ex) {
            throw new Exception("Não foi possível excluir a forma de pagamento informado" + ex.getMessage());
        }
    }
}
