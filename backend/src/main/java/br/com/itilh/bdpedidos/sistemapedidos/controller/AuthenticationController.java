package br.com.itilh.bdpedidos.sistemapedidos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.itilh.bdpedidos.sistemapedidos.dto.AuthLoginDTO;
import br.com.itilh.bdpedidos.sistemapedidos.dto.RegistroDTO;
import br.com.itilh.bdpedidos.sistemapedidos.dto.SingUpDTO;
import br.com.itilh.bdpedidos.sistemapedidos.model.Usuario;
import br.com.itilh.bdpedidos.sistemapedidos.repository.UsuarioRepository;
import br.com.itilh.bdpedidos.sistemapedidos.security.TokenService;


@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public SingUpDTO login(@RequestBody AuthLoginDTO dto) {
        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(dto.getLogin(), dto.getSenha());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((Usuario)auth.getPrincipal());

        return new SingUpDTO(dto.getLogin(), token);
    }

    @PostMapping("/registro")
    public String registro(@RequestBody RegistroDTO dto) {
        if(repository.findByLogin(dto.getLogin()) !=null)
            throw new RuntimeException("Usario já existe");
        String senhaCriptografado = new BCryptPasswordEncoder().encode(dto.getSenha());

        Usuario user = new Usuario(dto.getLogin(), senhaCriptografado, dto.role);
        repository.save(user);
        return "ok";
    }
    

}
