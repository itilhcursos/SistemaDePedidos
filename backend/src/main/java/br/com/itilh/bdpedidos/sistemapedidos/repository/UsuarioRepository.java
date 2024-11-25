package br.com.itilh.bdpedidos.sistemapedidos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.com.itilh.bdpedidos.sistemapedidos.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, String>{

    UserDetails findByLogin(String login);

}
