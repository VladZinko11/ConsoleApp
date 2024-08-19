package com.zinko.service;

import com.zinko.data.entity.User;
import com.zinko.service.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserServiceMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}
