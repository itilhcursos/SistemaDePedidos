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

import br.com.itilh.bdpedidos.sistemapedidos.dto.FormaPagamentoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.service.FormaPagamentoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController
@SecurityRequirement(name = "bearerAuth")
public class FormaPagamentoController {

    @Autowired
    FormaPagamentoService formaPagamentoService;

    @GetMapping("/formas-pagamento")
    public Page<FormaPagamentoDTO> getTodos(
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return formaPagamentoService.listarFormasPagamento(pageable);
    }

    @GetMapping("/formas-pagamento/{txtBusca}")
    public Page<FormaPagamentoDTO> getBusca(
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property,
        @PathVariable String txtBusca
    ) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return formaPagamentoService.buscar(pageable, txtBusca);
    }

    // @GetMapping("/formas-pagamento/descricao/{descricao}")
    // public List<FormaPagamentoDTO> getFormaPagamentoPorDescricao(@PathVariable String descricao,
    // @RequestParam(required = true) ModoBusca modoBusca) {
    //     if(modoBusca.equals(ModoBusca.EXATO)){
    //         return repositorio.findByDescricao(descricao);
    //     }else if (modoBusca.equals(ModoBusca.INICIADO)){
    //         return repositorio.findByDescricaoStartingWithIgnoreCase(descricao);
    //     }else if (modoBusca.equals(ModoBusca.FINALIZADO)){
    //         return repositorio.findByDescricaoStartingWithIgnoreCase(descricao);
    //     }else{
    //         return repositorio.findByDescricaoStartingWithIgnoreCase(descricao);
    //     }       
    // }

    @GetMapping("/forma-pagamento/{id}")
    public FormaPagamentoDTO getPorId(@PathVariable BigInteger id) throws Exception {
        return formaPagamentoService.buscarFormaPagamentoPorId(id);
    }  

    @PostMapping("/forma-pagamento")
    public FormaPagamentoDTO criarFormaPagamento(@RequestBody FormaPagamentoDTO dto) throws Exception { 
        return formaPagamentoService.criarFormaPagamento(dto);       
    }
    
    @PutMapping("/forma-pagamento/{id}")
    public FormaPagamentoDTO alterarFormaPagamento(@PathVariable BigInteger id, 
                                @RequestBody FormaPagamentoDTO novosDados) throws Exception {
            return formaPagamentoService.alterarFormaPagamento(id, novosDados);
    }

    @DeleteMapping("/forma-pagamento/{id}")
    public String deletePorId(@PathVariable BigInteger id) throws Exception {
        return formaPagamentoService.excluirFormaPagamento(id);
    }    

    
}
