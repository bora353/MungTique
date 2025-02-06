package com.mung.mungtique.mungshop.application.port.in;

import com.mung.mungtique.mungshop.adaptor.in.web.dto.image.ImageUploadReq;
import com.mung.mungtique.mungshop.adaptor.in.web.dto.image.ImageUploadRes;

import java.io.IOException;

public interface ImageService {
    ImageUploadRes upload(ImageUploadReq imageUploadReq) throws IOException;
}

