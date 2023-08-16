package com.security.demo.controller;

import com.security.demo.dto.DtoAuthRespuesta;
import com.security.demo.dto.DtoLogin;
import com.security.demo.dto.DtoRegistro;
import com.security.demo.models.Roles;
import com.security.demo.models.Usuarios;
import com.security.demo.repositories.IRolesRepository;
import com.security.demo.repositories.IUsuariosRepository;
import com.security.demo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth/")
public class RestControllerAuth {
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;

    private IRolesRepository iRolesRepository;
    private IUsuariosRepository iUsuariosRepository;
    private JwtTokenProvider jwtGenerador;

    @Autowired
    public RestControllerAuth(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, IRolesRepository iRolesRepository, IUsuariosRepository iUsuariosRepository, JwtTokenProvider jwtGenerador) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.iRolesRepository = iRolesRepository;
        this.iUsuariosRepository = iUsuariosRepository;
        this.jwtGenerador = jwtGenerador;
    }
    //registrarse con el rol user
    @PostMapping("register")
    public ResponseEntity<String> registrar(@RequestBody DtoRegistro dtoRegistro){
        if(iUsuariosRepository.existsByUsername(dtoRegistro.getUsername())){
            return  new ResponseEntity<>("Ya existe el Usuario", HttpStatus.BAD_REQUEST);
        }
        Usuarios usuarios=new Usuarios();
        usuarios.setUsername(dtoRegistro.getUsername());
        usuarios.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));
        Roles roles=iRolesRepository.findByName("USER") .get();
        usuarios.setRoles(Collections.singletonList(roles));
        iUsuariosRepository.save(usuarios);
        return new ResponseEntity<>("Se registro correctamente",HttpStatus.OK);
    }
    @PostMapping("login")
    public ResponseEntity<DtoAuthRespuesta>login(@RequestBody DtoLogin dtoLogin){
        Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dtoLogin.getUsername(),dtoLogin.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerador.generarToken(authentication);
        return new ResponseEntity<>(new DtoAuthRespuesta(token),HttpStatus.OK);
    }
}
