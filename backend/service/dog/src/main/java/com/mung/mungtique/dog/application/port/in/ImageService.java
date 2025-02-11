package com.mung.mungtique.dog.application.port.in;



import com.mung.mungtique.dog.adaptor.in.web.dto.image.ImageUploadRes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    ImageUploadRes upload(Long dogId, MultipartFile imageFile) throws IOException;
}

