package com.example.unitech.resource.mapper;

import org.mapstruct.MappingTarget;
import org.springframework.data.domain.Page;

public interface ResourceEntityMapper<R, E> {

    E from(R resource);

    R to(E entity);

    void mapFrom(@MappingTarget E entity, R resource);

    void mapTo(@MappingTarget R resource, E entity);


}
