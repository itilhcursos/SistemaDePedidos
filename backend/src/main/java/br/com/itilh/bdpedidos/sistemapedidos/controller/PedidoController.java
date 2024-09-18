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


import br.com.itilh.bdpedidos.sistemapedidos.dto.PedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.service.PedidoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class PedidoController {

    @Autowired
    PedidoService pedidoService;



    @GetMapping("/Pedidos")
    public Page<PedidoDTO> BuscarPedidos(
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return pedidoService.listarPedidos(pageable);
    }
     @GetMapping("/Pedidos/cliente-id/{id}")
    public Page<PedidoDTO> BuscarPedidosPorClienteId(@PathVariable BigInteger id,
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ){
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return pedidoService.listarPedidoPorClienteId(id, pageable);
    }


    @GetMapping("/Pedidos/formaPagamento-id/{id}")
    public Page<PedidoDTO> BuscarPedidosPorformaPagamentoId(@PathVariable BigInteger id,
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ){
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return pedidoService.listarPedidoPorFormaPagamentoId(id, pageable);
    }


    @GetMapping("/Pedidos/cliente-nome/{nome}")
    public Page<PedidoDTO> BuscarPedidosPorClienteNome(@PathVariable String nome,
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ){
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return pedidoService.listarPedidoPorClienteNome(nome, pageable);
    }


    @GetMapping("/Pedidos/formaPagamento-nome/{nome}")
    public Page<PedidoDTO> BuscarPedidosPorFormaPagamentoNome(@PathVariable String nome,
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ){
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return pedidoService.listarPedidoPorFormaPagamentoNome(nome, pageable);
    }



      @GetMapping("/Pedido/{id}")
    public PedidoDTO BuscarPedidoPorId(@PathVariable BigInteger id) throws Exception {
        return pedidoService.buscarPedidoPorId(id);
    }    


    @PostMapping("/Pedido")
    public PedidoDTO criarPedido(@RequestBody PedidoDTO entity) throws Exception {
        return pedidoService.criarPedido(entity);
    }
    

    @PutMapping("/ItemPedido/{id}")
    public PedidoDTO alterarItemPedido(@PathVariable BigInteger id, @RequestBody PedidoDTO origem) throws Exception {
        return pedidoService.alterarPedido(id,origem);
    }


    @DeleteMapping("/Pedido/{id}")
    public String deletePedido(@PathVariable BigInteger id) throws Exception {
        return pedidoService.excluirPedido(id);
    }
}
