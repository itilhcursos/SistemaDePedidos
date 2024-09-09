package br.com.itilh.bdpedidos.sistemapedidos.security;

import org.springframework.stereotype.Service;

import com.auth0.jwt.algorithms.Algorithm;

import br.com.itilh.bdpedidos.sistemapedidos.model.Usuario;

@Service
public class TokenService {

    private String secret = "Cursos_Itilh";

    public String generateToken (Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
             
        }
    }

}
