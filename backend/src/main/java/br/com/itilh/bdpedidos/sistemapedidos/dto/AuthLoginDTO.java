package br.com.itilh.bdpedidos.sistemapedidos.dto;

<<<<<<< HEAD
import lombok.Data;


@Data
=======
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
>>>>>>> develop
public class AuthLoginDTO {

    private String login;
    private String senha;

}
