package com.mung.mungtique.user.application.service.mapper;

import com.mung.mungtique.user.adaptor.in.web.dto.JoinDTO;
import com.mung.mungtique.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface UserMapper {

    //UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastLoginAt", ignore = true) // 추가된 라인
    User joinDtoToUserEntity(JoinDTO joinDTO, String role);
}
