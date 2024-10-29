package br.com.itilh.bdpedidos.sistemapedidos.controller;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.itilh.bdpedidos.sistemapedidos.dto.ItemPedidoDTO;
import br.com.itilh.bdpedidos.sistemapedidos.service.ItemPedidoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name="bearerAuth")
public class ItemPedidoController {

    @Autowired
    ItemPedidoService ItemPedidoservice;
   

    @PostMapping("/item-pedido")
    public ItemPedidoDTO criarItemPedido(@RequestBody ItemPedidoDTO dto) throws Exception {
        return ItemPedidoservice.criarItemPedido(dto);
    }

    @DeleteMapping("/item-pedido/{id}")
    public String deleteItemPedido(@PathVariable BigInteger id) throws Exception {
        return ItemPedidoservice.deleteItemPedido(id);
    }
}