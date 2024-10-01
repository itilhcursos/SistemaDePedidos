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
@SecurityRequirement(name = "bearerAuth")
public class ItemPedidoController {

    @Autowired
    ItemPedidoService itemPedidoService;

// @GetMapping("/estados")
//     public Page<EstadoDTO> getTodos(
//         @RequestParam(required = false, defaultValue = "1") int pageNumber,
//         @RequestParam(required = false, defaultValue = "10") int pageSize,
//         @RequestParam(required = false, defaultValue = "ASC") String direction,
//         @RequestParam(required = false, defaultValue = "id") String property
//     ) {
//         Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.valueOf(direction), property);

//         return estadoService.getTodos(pageable);
//     }

    // @GetMapping("/estados/nome/{nome}")
    // public List<Estado> getEstadosPorNome(@PathVariable String nome,
    // @RequestParam(required = true) ModoBusca modoBusca) {
    //     if(modoBusca.equals(ModoBusca.EXATO)){
    //         return repositorio.findByNome(nome);
    //     }else if (modoBusca.equals(ModoBusca.INICIADO)){
    //         return repositorio.findByNomeStartingWithIgnoreCase(nome);
    //     }else if (modoBusca.equals(ModoBusca.FINALIZADO)){
    //         return repositorio.findByNomeEndingWithIgnoreCase(nome);
    //     }else{
    //         return repositorio.findByNomeContainingIgnoreCase(nome);
    //     }       
    // }
        
    // @GetMapping("/estado/{id}")
    // public EstadoDTO getPorId(@PathVariable BigInteger id) throws Exception {
    //     return estadoService.getPorId(id);
    // }    

    @PostMapping("/item-pedido")
    public ItemPedidoDTO criarItemPedido(@RequestBody ItemPedidoDTO entityDTO) throws Exception { 
       return itemPedidoService.criarItemPedido(entityDTO);
    }

    // @PutMapping("/estado/{id}")
    // public EstadoDTO alterarEstado(@PathVariable BigInteger id, 
    //                             @RequestBody EstadoDTO novosDados) throws Exception {

    //     return estadoService.alterarEstado(id, novosDados);
    // }

    @DeleteMapping("/item-pedido/{id}")
    public String deletePorId(@PathVariable BigInteger id) throws Exception {
        return itemPedidoService.excluirItemPedido(id);
    }    
}
