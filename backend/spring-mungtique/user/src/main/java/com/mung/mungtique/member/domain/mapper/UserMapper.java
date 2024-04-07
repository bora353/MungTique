package com.mung.mungtique.member.domain.mapper;

import com.mung.mungtique.member.adaptor.in.web.dto.JoinDTO;
import com.mung.mungtique.member.adaptor.out.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper (componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    
    // TODO : 권한 default로 ADMIN으로 했는데 수정 필요
    @Mapping(target = "id", ignore = true)
    UserEntity joinDtoToUserEntity(JoinDTO joinDTO, String role);
}
