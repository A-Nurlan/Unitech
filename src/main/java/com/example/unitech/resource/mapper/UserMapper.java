package com.example.unitech.resource.mapper;

import com.example.unitech.data.entity.User;
import com.example.unitech.resource.UserResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends ResourceEntityMapper<UserResource, User> {

    @Mapping(target = UserResource.Fields.password, ignore = true)
    @Override
    UserResource  to(User entity);

    @Mapping(target = User.Fields.password, ignore = true)
    @Override
    User from(UserResource resource);

    @Mapping(target = UserResource.Fields.password, ignore = true)
    @Override
    void mapTo(@MappingTarget UserResource resource, User entity);

    @Mapping(target = User.Fields.password, ignore = true)
    @Override
    void mapFrom(@MappingTarget User resource, UserResource entity);

}
