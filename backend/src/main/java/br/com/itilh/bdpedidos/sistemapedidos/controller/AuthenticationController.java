package br.com.itilh.bdpedidos.sistemapedidos.controller;

<<<<<<< HEAD
=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
>>>>>>> develop
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itilh.bdpedidos.sistemapedidos.dto.AuthLoginDTO;
import br.com.itilh.bdpedidos.sistemapedidos.dto.RegistroDTO;
import br.com.itilh.bdpedidos.sistemapedidos.model.Usuario;
import br.com.itilh.bdpedidos.sistemapedidos.repository.UsuarioRepository;

<<<<<<< HEAD
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("auth")
public class AuthenticationController {
=======
@RestController
@RequestMapping("auth")
public class AuthenticationController { 
>>>>>>> develop

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private AuthenticationManager authenticationManager;

<<<<<<< HEAD
     @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public String login(@RequestBody AuthLoginDTO dto) {

        UsernamePasswordAuthenticationToken loginSenha = new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getSenha());

        var auth = this.authenticationManager.authenticate(loginSenha);
        var token = tokenService.generateToken((Usuario)auth.getPrincipal());

        return token;
    };

    @PostMapping("/registro")
    public String postMethodName(@RequestBody RegistroDTO dto) {

        if(repository.findByLogin(dto.getLogin())!=null)
            throw new RuntimeException("Login já existe.");

            String senhaCriptografada = new BCryptPasswordEncoder().encode(dto.getSenha());

            Usuario user = new Usuario(dto.getLogin(),senhaCriptografada, dto.getRole());
            repository.save(user);
        

        return "Usuário criado com Sucesso";
=======
    @PostMapping("/login")
    public String login (@RequestBody AuthLoginDTO dto){
        UsernamePasswordAuthenticationToken loginSenha = new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getSenha());

        var auth = this.authenticationManager.authenticate(loginSenha);

        return auth.toString();

    }

    @PostMapping("/registro")
    public String registro (@RequestBody RegistroDTO dto){

        if(repository.findByLogin(dto.getLogin())!=null)
            throw new RuntimeException("Login já existe");

            String senhaCriptografada = new BCryptPasswordEncoder().encode(dto.getSenha());
            Usuario user = new Usuario(dto.getLogin(),senhaCriptografada, dto.getRole());
            repository.save(user); 

        return "Usuário registrado com sucesso.";
>>>>>>> develop
    }

}
