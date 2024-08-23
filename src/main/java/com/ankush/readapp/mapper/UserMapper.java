package com.ankush.readapp.mapper;

import com.ankush.readapp.dto.UserRegistrationRequest;
import com.ankush.readapp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID().toString())")
    @Mapping(target = "password", ignore = true)
    User toEntity(UserRegistrationRequest userRegistrationRequest);
}
