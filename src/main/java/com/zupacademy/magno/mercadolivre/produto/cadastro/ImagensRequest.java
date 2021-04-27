package com.zupacademy.magno.mercadolivre.produto.cadastro;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class ImagensRequest {

    @NotNull @Size(min = 1)
    private List<MultipartFile> imagens = new ArrayList<>();

    public ImagensRequest(List<MultipartFile> imagens) {
        this.imagens = imagens;
    }
}
