package br.com.itilh.bdpedidos.sistemapedidos.security;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.itilh.bdpedidos.sistemapedidos.model.Usuario;

@Service
public class TokenService {

    private String secret = "Cursos_Itilh";

    public String generateToken (Usuario usuario){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                            .withIssuer("SistemaDePedidos")
                            .withSubject(usuario.getLogin())
                            .withExpiresAt(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")))
                            .sign(algorithm);
            return token;

        }catch( Exception e){
            throw new RuntimeException("Erro de token");
        }
    }

    public String validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                            .withIssuer("SistemaDePedidos")
                            .build()
                            .verify(token)
                            .getSubject();

        }catch(Exception e){
            return "";
        }
    }

}
