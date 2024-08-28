package br.com.itilh.bdpedidos.sistemapedidos.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class  ProdutoEstoqueNegativoException extends RuntimeException{
    
    // public ProdutoEstoqueNegativoException(String descricao, Double quantidadeEstoque){
    //     super(" O Estoque do Produto = " + descricao + "esta com" + quantidadeEstoque + ": estoque Negativo");
    // }

    public ProdutoEstoqueNegativoException(Double quantidadeEstoque) {
        super(" O Estoque do Produto = esta com" + quantidadeEstoque + ": estoque Negativo");
    }

   
}
