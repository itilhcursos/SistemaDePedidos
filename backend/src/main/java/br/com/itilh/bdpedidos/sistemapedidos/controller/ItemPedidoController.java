package br.com.itilh.bdpedidos.sistemapedidos.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ItemPedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.service.ItemPedidoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@SecurityRequirement(name = "bearerAuth")
public class ItemPedidoController {

    @Autowired
    ItemPedidoService service;

    @PostMapping("/item-pedido")
    public ItemPedidoDTO criarItemPedido(@RequestBody ItemPedidoDTO entityDTO) throws Exception {
        return service.criarItemPedido(entityDTO);
    }

    @DeleteMapping("/item-pedido/{id}")
    public String deletePorId(@PathVariable BigInteger id) throws Exception {
        return service.deletePorId(id);
    }  
    

}