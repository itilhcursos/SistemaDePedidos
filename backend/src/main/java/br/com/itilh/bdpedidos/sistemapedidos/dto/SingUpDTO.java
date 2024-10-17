package br.com.itilh.bdpedidos.sistemapedidos.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SingUpDTO {
    public SingUpDTO(String login2, Object token2) {
        //TODO Auto-generated constructor stub
    }
    public String login;
    public String token;
}
