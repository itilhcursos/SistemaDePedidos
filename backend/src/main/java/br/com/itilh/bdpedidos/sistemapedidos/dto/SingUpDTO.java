package br.com.itilh.bdpedidos.sistemapedidos.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SingUpDTO {

    private String login;
    private String token;
}