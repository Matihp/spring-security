package com.security.demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider  {
    //crea el token por medio de autenticacion
    public String generarToken(Authentication authentication){
        String username = authentication.getName();
        Date tiempoActual=new Date();
        Date expiracionToken = new Date(tiempoActual.getTime()+ ConstantesSecurity.JWT_EXPIRATION_TOKEN);

        String token= Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(expiracionToken)
                .signWith(SignatureAlgorithm.HS512,ConstantesSecurity.JWT_FIRMA).compact();
        return token;
    }
    //extrae  username con el token
    public String extraerUsernameJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(ConstantesSecurity.JWT_FIRMA).parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    public Boolean validarToken (String token){
        try{
            Jwts.parser().setSigningKey(ConstantesSecurity.JWT_FIRMA).parseClaimsJws(token);
            return true;
        }catch(Exception e){
            throw new AuthenticationCredentialsNotFoundException("Esta mal el jwt");
        }
    }
}
