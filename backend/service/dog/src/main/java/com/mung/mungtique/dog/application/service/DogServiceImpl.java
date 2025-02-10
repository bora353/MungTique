package com.mung.mungtique.dog.application.service;


import com.mung.mungtique.dog.adaptor.in.web.dto.image.ImageUploadRes;
import com.mung.mungtique.dog.adaptor.in.web.dto.mung.DogJoinReq;
import com.mung.mungtique.dog.adaptor.in.web.dto.mung.DogRes;
import com.mung.mungtique.dog.application.port.in.DogService;
import com.mung.mungtique.dog.application.port.out.DogRepoPort;
import com.mung.mungtique.dog.application.service.mapper.DogMapper;
import com.mung.mungtique.dog.domain.Dog;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DogServiceImpl implements DogService {

    private final DogRepoPort dogRepoPort;
    private final DogMapper mapper;

    @Override
    @Transactional
    public Dog join(DogJoinReq DogJoinReq) {
        Dog dog = mapper.dtoToDomain(DogJoinReq);

        return dogRepoPort.save(dog);
    }

    @Override
    public List<DogRes> getDogs(Long userId) {

        List<Dog> byUserId = dogRepoPort.findByUserId(userId);
        List<DogRes> DogResList = mapper.domainListToDtoList(byUserId);

        for (DogRes DogRes : DogResList) {
            if (DogRes.getImage() == null) continue; // TODO : default image 나오게

            String[] split = DogRes.getImage().url().split("/");
            String realImageUrl = split[split.length - 1];
            String imageUrl =  "C:\\temp\\" + realImageUrl; // TODO : S3 서버 사용하자

            DogRes.setImage(new ImageUploadRes(imageUrl));
        }
        
        // TODO : 예외처리로 기본 이미지 보여지게 해야함(메서드 수정 많이 필요)

        return DogResList;
    }
}
