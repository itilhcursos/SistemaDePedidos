package br.com.itilh.bdpedidos.sistemapedidos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.itilh.bdpedidos.sistemapedidos.repository.UsuarioRepository;

@Service
<<<<<<< HEAD
public class AuthorizationService implements UserDetailsService{
    
=======
public class AuthorizationService  implements UserDetailsService{

>>>>>>> develop
    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
<<<<<<< HEAD
        return repository.findByLogin(username);
=======
       return repository.findByLogin(username);
>>>>>>> develop
    }

}
