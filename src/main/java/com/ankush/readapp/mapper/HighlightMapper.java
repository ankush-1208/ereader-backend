package com.ankush.readapp.mapper;

import com.ankush.readapp.dto.HighlightRequest;
import com.ankush.readapp.entity.Highlight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.security.core.userdetails.UserDetails;

@Mapper(componentModel = "spring")
public interface HighlightMapper {

    HighlightMapper INSTANCE = Mappers.getMapper(HighlightMapper.class);

    @Mapping(target = "userId", source = "userDetails.id")
    Highlight toEntity(HighlightRequest highlightRequest, UserDetails userDetails);
}
