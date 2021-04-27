package com.zupacademy.magno.mercadolivre.produto.cadastro.imagens;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Primary
public class UploaderFalso implements Uploader {

    /**
     * recolhe as imagens passadas como parametro e devolve um Set com os links
     * @param imagens lista de imagens do tipo MultiPartFile
     * @return links para imagens que foram carregadas
     */
    public Set<String> envia(List<MultipartFile> imagens) {
        return imagens.stream()
                .map(i -> "http://bucket.io/"+ i.getOriginalFilename())
                .collect(Collectors.toSet());
    }
}
