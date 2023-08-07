package com.security.demo.security;

import com.security.demo.models.Roles;
import com.security.demo.models.Usuarios;
import com.security.demo.repositories.IUsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService  {
    private IUsuariosRepository usuariosRepository;
    @Autowired
    public CustomUserDetailsService(IUsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }
    public Collection<GrantedAuthority>mapToAuthorities(List<Roles>roles){
        return roles.stream().map(rol -> new SimpleGrantedAuthority(rol.getName())).collect(Collectors.toList());

    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuarios usuarios = usuariosRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("No se encontro el usuario"));
        return new User(usuarios.getUsername(),usuarios.getPassword(),mapToAuthorities(usuarios.getRoles()));
    }
}
