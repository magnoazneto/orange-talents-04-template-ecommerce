package com.zupacademy.magno.mercadolivre.config.seguranca.autenticacao;

import com.zupacademy.magno.mercadolivre.config.seguranca.autenticacao.dto.LoginRequest;
import com.zupacademy.magno.mercadolivre.config.seguranca.autenticacao.dto.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid LoginRequest request){
        UsernamePasswordAuthenticationToken dadosLogin = request.gerarDadosParaToken();

        Authentication auth = authManager.authenticate(dadosLogin);
        String token = tokenService.gerarToken(auth);

        return ResponseEntity.ok(new TokenDto(token, "Bearer"));
    }
}
