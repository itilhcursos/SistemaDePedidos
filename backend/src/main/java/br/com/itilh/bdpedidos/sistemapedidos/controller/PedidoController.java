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

    @GetMapping("/pedidos")
    public Page<PedidoDTO> getTodos(
            @RequestParam(required = false, defaultValue = "1") int pageNumber,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            @RequestParam(required = false, defaultValue = "ASC") String direction,
            @RequestParam(required = false, defaultValue = "id") String property) {
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);

        return pedidoService.getTodos(pageable);
    }

    @GetMapping("/pedido/{id}")
    public PedidoDTO getPorId(@PathVariable BigInteger id) throws Exception {
        return pedidoService.getPorId(id);
    }

    @PostMapping("/pedido")
    public PedidoDTO criarPedido(@RequestBody PedidoDTO entityDTO) throws Exception {
        return pedidoService.criarPedido(entityDTO);
    }

    @PutMapping("/pedido/{id}")
    public PedidoDTO alterarPedido(@PathVariable BigInteger id,
            @RequestBody PedidoDTO novosDados) throws Exception {

        return pedidoService.alterarPedido(id, novosDados);
    }

    @DeleteMapping("/pedido/{id}")
    public String deletePorId(@PathVariable BigInteger id) throws Exception {
        return pedidoService.deletePorId(id);
    }
}
