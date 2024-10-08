package br.com.itilh.bdpedidos.sistemapedidos.dto;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class EstadoDTO {
    
    private BigInteger id;
    private String nome;


    public void validarDados() throws Exception{
        if(this.nome == null || "".equals(this.nome)){
            throw new Exception("Nome do estado não pode ser vazio");
        }
    }
}
