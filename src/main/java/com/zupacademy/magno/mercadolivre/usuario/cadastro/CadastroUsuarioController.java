package com.zupacademy.magno.mercadolivre.usuario.cadastro;

import com.zupacademy.magno.mercadolivre.usuario.Usuario;
import com.zupacademy.magno.mercadolivre.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class CadastroUsuarioController {

    @Autowired
    UsuarioRepository usuarioRepo;

    @PostMapping
    @Transactional
    public ResponseEntity<?> criaUsuario(@RequestBody @Valid CadastroUsuarioRequest request){
        Usuario novoUsuario = request.toModel();
        usuarioRepo.save(novoUsuario);
        return ResponseEntity.ok().build();
    }
}
