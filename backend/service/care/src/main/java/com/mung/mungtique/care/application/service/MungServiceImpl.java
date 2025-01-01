package com.mung.mungtique.care.application.service;

import com.mung.mungtique.care.adaptor.in.web.dto.image.ImageUploadRes;
import com.mung.mungtique.care.adaptor.in.web.dto.mung.MungJoinReq;
import com.mung.mungtique.care.adaptor.in.web.dto.mung.MungRes;
import com.mung.mungtique.care.application.port.in.MungService;
import com.mung.mungtique.care.application.port.out.MungRepoPort;
import com.mung.mungtique.care.application.service.mapper.MungMapper;
import com.mung.mungtique.care.domain.MyMung;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MungServiceImpl implements MungService {

    private final MungRepoPort mungRepoPort;
    private final MungMapper mapper;

    @Override
    @Transactional
    public MyMung join(MungJoinReq mungJoinReq) {
        MyMung myMung = mapper.dtoToDomain(mungJoinReq);

        return mungRepoPort.save(myMung);
    }

    @Override
    public List<MungRes> getMyMungs(Long userId) {

        List<MyMung> byUserId = mungRepoPort.findByUserId(userId);
        List<MungRes> mungResList = mapper.domainListToDtoList(byUserId);

        for (MungRes mungRes : mungResList) {
            if (mungRes.getImage() == null) continue; // TODO : default image 나오게

            String[] split = mungRes.getImage().url().split("/");
            String realImageUrl = split[split.length - 1];
            String imageUrl =  "C:\\temp\\" + realImageUrl; // TODO : S3 서버 사용하자

            mungRes.setImage(new ImageUploadRes(imageUrl));
        }

        return mungResList;
    }
}
