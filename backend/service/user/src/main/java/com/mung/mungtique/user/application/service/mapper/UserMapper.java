package com.mung.mungtique.user.application.service.mapper;

import com.mung.mungtique.user.adaptor.in.web.dto.JoinReq;
import com.mung.mungtique.user.adaptor.in.web.dto.JoinRes;
import com.mung.mungtique.user.domain.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper (componentModel = "spring")
public interface UserMapper {

    //UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "lastLoginAt", ignore = true) // 추가된 라인
    UserEntity toUserEntity(JoinReq joinReq, String role);

    JoinRes toJoinRes(UserEntity userEntity);
}
