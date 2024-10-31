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

// LISTAGENS (GET) //

    // Listar todas as formas de pagamento
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

    // Listar formas de pagamento filtrando por texto
    @GetMapping("/formas-pagamento/{txtBusca}")
    public Page<FormaPagamentoDTO> getFormasPagamento(
            @RequestParam(required = false, defaultValue = "1") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "ASC") String direction,
            @RequestParam(required = false, defaultValue = "id") String property,
            @PathVariable String txtBusca
            ) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return formaPagamentoService.buscar(pageable, txtBusca);
    }

    // Listar formas de pagamento por ID
    @GetMapping("/forma-pagamento/{id}")
    public FormaPagamentoDTO getPorId(@PathVariable BigInteger id) throws Exception {
        return formaPagamentoService.buscarFormaPagamentoPorId(id);
    }  

// POST - PUT - DELETE //

    // Criar registro de forma de pagamento
    @PostMapping("/forma-pagamento")
    public FormaPagamentoDTO criarFormaPagamento(@RequestBody FormaPagamentoDTO dto) throws Exception { 
        return formaPagamentoService.criarFormaPagamento(dto);       
    }
    
    // Atualizar registro de forma de pagamento
    @PutMapping("/forma-pagamento/{id}")
    public FormaPagamentoDTO alterarFormaPagamento(@PathVariable BigInteger id, @RequestBody FormaPagamentoDTO novosDados) throws Exception {
            return formaPagamentoService.alterarFormaPagamento(id, novosDados);
    }

    // Excluir registro de forma de pagamento
    @DeleteMapping("/forma-pagamento/{id}")
    public String deletePorId(@PathVariable BigInteger id) throws Exception {
        return formaPagamentoService.excluirFormaPagamento(id);
    }    
}
