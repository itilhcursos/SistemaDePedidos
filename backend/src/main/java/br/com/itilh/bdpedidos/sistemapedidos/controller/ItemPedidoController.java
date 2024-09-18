package br.com.itilh.bdpedidos.sistemapedidos.controller;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

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

import br.com.itilh.bdpedidos.sistemapedidos.dto.ItemPedidoDTO;

import br.com.itilh.bdpedidos.sistemapedidos.service.ItemPedidoService;
@RestController
@SecurityRequirement(name = "bearerAuth")
public class ItemPedidoController {

@Autowired
ItemPedidoService itemPedidoService;

 @GetMapping("/ItemPedidos")
    public Page<ItemPedidoDTO> BuscarItemPedidos(
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return itemPedidoService.listarItemPedidos(pageable);
    }
     @GetMapping("/ItemPedidos/pedido-id/{id}")
    public Page<ItemPedidoDTO> BuscarItemPedidosPorPedidoId(@PathVariable BigInteger id,
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ){
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return itemPedidoService.listarItemPedidoPorPedidoId(id, pageable);
    }

    @GetMapping("/ItemPedidos/produto-nome/{nome}")
    public Page<ItemPedidoDTO> BuscarItemPedidosPorProdutoNome(@PathVariable String nome,
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ){
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return itemPedidoService.listarItemPedidoPorProdutoNome(nome, pageable);
    }
    @GetMapping("/ItemPedidos/produto-id/{id}")
    public Page<ItemPedidoDTO> BuscarItemPedidosPorProdutoId(@PathVariable BigInteger id,
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ){
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return itemPedidoService.listarItemPedidoPorProdutoId(id, pageable);
    }
    @GetMapping("/ItemPedido/{id}")
    public ItemPedidoDTO BuscarItemPedidoPorId(@PathVariable BigInteger id) throws Exception {
        return itemPedidoService.buscarItemPedidoPorId(id);
    }    

    @PostMapping("/ItemPedido")
    public ItemPedidoDTO criarItemPedido(@RequestBody ItemPedidoDTO entity) throws Exception {
        return itemPedidoService.criarItemPedido(entity);
    }
    
    @PutMapping("/ItemPedido/{id}")
    public ItemPedidoDTO alterarItemPedido(@PathVariable BigInteger id, @RequestBody ItemPedidoDTO origem) throws Exception {
        return itemPedidoService.alterarItemPedido(id,origem);
    }

    @DeleteMapping("/ItemPedido/{id}")
    public String deleteItemPedido(@PathVariable BigInteger id) throws Exception {
        return itemPedidoService.excluirItemPedido(id);
    }
}
