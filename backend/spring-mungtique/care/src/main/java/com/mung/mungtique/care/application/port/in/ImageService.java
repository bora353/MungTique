package com.mung.mungtique.care.application.port.in;

import com.mung.mungtique.care.adaptor.in.web.dto.ImageUploadReq;
import com.mung.mungtique.care.adaptor.in.web.dto.ImageUploadRes;

import java.io.IOException;

public interface ImageService {
    ImageUploadRes upload(ImageUploadReq imageUploadReq) throws IOException;
}

