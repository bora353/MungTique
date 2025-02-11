package com.mung.mungtique.dog.application.service.mapper;

import com.mung.mungtique.dog.adaptor.in.web.dto.mung.DogJoinReq;
import com.mung.mungtique.dog.adaptor.in.web.dto.mung.DogRes;
import com.mung.mungtique.dog.domain.Dog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Mapper (componentModel = "spring")
public interface DogMapper {

    Dog dtoToDomain(DogJoinReq DogJoinReq);
//    List<DogRes> domainListToDtoList(List<Dog> byUserId);

    @Mapping(target = "imageUrl", source = "image.url", defaultValue = "https://cdn.pixabay.com/photo/2018/05/26/18/06/dog-3431913_1280.jpg")
    DogRes domainToDto(Dog dog);
}
