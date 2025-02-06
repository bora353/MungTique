package com.mung.mungtique.dog.application.port.in;



import com.mung.mungtique.dog.adaptor.in.web.dto.image.ImageUploadReq;
import com.mung.mungtique.dog.adaptor.in.web.dto.image.ImageUploadRes;

import java.io.IOException;

public interface ImageService {
    ImageUploadRes upload(ImageUploadReq imageUploadReq) throws IOException;
}

