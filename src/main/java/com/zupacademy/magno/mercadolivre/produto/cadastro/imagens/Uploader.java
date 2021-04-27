package com.zupacademy.magno.mercadolivre.produto.cadastro.imagens;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface Uploader {

    public Set<String> envia(List<MultipartFile> imagens);
}
