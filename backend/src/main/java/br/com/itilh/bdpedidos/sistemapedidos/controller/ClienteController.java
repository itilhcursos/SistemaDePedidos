package br.com.itilh.bdpedidos.sistemapedidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.com.itilh.bdpedidos.sistemapedidos.service.FormaPagamentoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "bearerAuth")
public class ClienteController {
    @Autowired
    FormaPagamentoService formaPagamentoService;

    

}
