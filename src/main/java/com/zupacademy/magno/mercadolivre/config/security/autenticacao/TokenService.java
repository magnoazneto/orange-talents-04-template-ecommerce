package com.zupacademy.magno.mercadolivre.config.security.autenticacao;

import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import com.zupacademy.magno.mercadolivre.usuario.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.Optional;

@Service
public class TokenService {

    @Value("${mercadolivre.jwt.expiration}")
    private String expiration;
    @Value("${mercadolivre.jwt.secret}")
    private String secret;

    @Autowired
    private UsuarioRepository usuarioRepository;


    public String gerarToken(Authentication authentication) {

        Usuario logado = (Usuario) authentication.getPrincipal();
        Date hoje = new Date();
        Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));

        return  Jwts.builder()
                .setIssuer("API ecommerce bootcamp")
                .setSubject(logado.getId().toString())
                .setIssuedAt(hoje)
                .setExpiration(dataExpiracao)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isTokenValido(String token) {
        try{
            Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    public Usuario getUsuario(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        Optional<Usuario> usuario = usuarioRepository.findById(Long.parseLong(claims.getSubject()));
        Assert.notNull(usuario, "Problema ao encontrar usuario.");
        return usuario.get();
    }

}
