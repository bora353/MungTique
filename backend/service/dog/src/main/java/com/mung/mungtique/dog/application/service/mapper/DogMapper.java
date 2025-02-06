package com.mung.mungtique.dog.application.service.mapper;

import com.mung.mungtique.dog.adaptor.in.web.dto.mung.DogJoinReq;
import com.mung.mungtique.dog.adaptor.in.web.dto.mung.DogRes;
import com.mung.mungtique.dog.domain.Dog;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper (componentModel = "spring")
public interface DogMapper {

    Dog dtoToDomain(DogJoinReq DogJoinReq);

    List<DogRes> domainListToDtoList(List<Dog> byUserId);

    DogRes domainToDto(Dog dog);
}
