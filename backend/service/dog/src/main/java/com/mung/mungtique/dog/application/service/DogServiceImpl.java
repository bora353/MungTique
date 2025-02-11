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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        List<Dog> dogs = dogRepoPort.findByUserId(userId);

        return dogs.isEmpty() ? Collections.emptyList() : dogs.stream()
                .map(mapper::domainToDto)
                .collect(Collectors.toList());
    }
}
