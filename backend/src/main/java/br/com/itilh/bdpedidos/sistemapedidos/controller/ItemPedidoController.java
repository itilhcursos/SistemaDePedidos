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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ItemPedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.service.ItemPedidoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name="bearerAuth")
public class ItemPedidoController {

    @Autowired
    ItemPedidoService service;

    @GetMapping("/itens-pedido")
    public Page<ItemPedidoDTO> getItensPedido(
        @RequestParam(required = false, defaultValue = "1") int pageNumber,
        @RequestParam(required = false, defaultValue = "10") int pageSize,
        @RequestParam(required = false, defaultValue = "ASC") String direction,
        @RequestParam(required = false, defaultValue = "id") String property
    ) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);
        return service.listarItensPedido(pageable);
    }

    @PostMapping("/item-pedido")
    public ItemPedidoDTO criarItemPedido(@RequestBody ItemPedidoDTO entityDTO) throws Exception {
        return service.criarItemPedido(entityDTO);
    }

    @DeleteMapping("/item-pedido/{id}")
    public String deletePorId(@PathVariable BigInteger id) throws Exception {
        return service.deletePorId(id);
    }
    
}
