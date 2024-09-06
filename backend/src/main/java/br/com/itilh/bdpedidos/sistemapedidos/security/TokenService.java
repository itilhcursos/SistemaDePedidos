package br.com.itilh.bdpedidos.sistemapedidos.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.itilh.bdpedidos.sistemapedidos.model.Usuario;

@Service
public class TokenService  {
    private String secret = "Cursos_Itilh";

    public String generateToken (Usuario usuario){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("sistemaDePedidos")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(getExpirestionDate())
                    .sign(algorithm);
           return token;
       }catch(Exception exception) {
            throw new RuntimeException("Erro de token");
       }
     
    }

    public String validateToken (String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                .withIssuer("sistemaDePedidos")
                .build()
                .verify(token)
                .getSubject();
       }catch(Exception exception) {
            return "";
       }     
    }

    private Instant getExpirestionDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

}
