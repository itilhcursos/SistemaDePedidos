package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ItemPedidoEstoqueNegativoException  extends RuntimeException{
public ItemPedidoEstoqueNegativoException(Double quantidadeEstoque){

    super("ItemPedido = " + quantidadeEstoque + "Negativo");
}
}